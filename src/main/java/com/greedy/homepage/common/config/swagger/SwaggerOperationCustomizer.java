package com.greedy.homepage.common.config.swagger;

import com.greedy.homepage.common.exception.FailMessage;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SwaggerOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        List<FailMessage> failMessages = extractFailMessages(handlerMethod);

        if (failMessages.isEmpty()) {
            addDefaultErrorResponses(operation, handlerMethod);
            return operation;
        }

        // 500 자동 추가
        if (failMessages.stream().noneMatch(f -> f.getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR)) {
            failMessages.add(FailMessage.INTERNAL_SERVER_ERROR);
        }

        String path = resolveRequestPath(handlerMethod);
        ApiResponses responses = operation.getResponses();

        Map<HttpStatus, List<FailMessage>> grouped = failMessages.stream()
                .collect(Collectors.groupingBy(FailMessage::getHttpStatus));

        grouped.forEach((status, codes) -> {
            ApiResponse response = new ApiResponse()
                    .description(status.getReasonPhrase());

            MediaType mediaType = new MediaType()
                    .schema(new Schema<>().$ref("#/components/schemas/APIErrorResponse"));

            for (FailMessage failMessage : codes) {
                Example example = new Example();
                example.setValue(Map.of(
                        "code", failMessage.getCode(),
                        "message", failMessage.getMessage()
                ));
                example.setDescription(failMessage.name());
                mediaType.addExamples(failMessage.name(), example);
            }

            response.setContent(new Content().addMediaType("application/json", mediaType));
            responses.addApiResponse(String.valueOf(status.value()), response);
        });

        return operation;
    }

    private List<FailMessage> extractFailMessages(HandlerMethod handlerMethod) {
        List<FailMessage> failMessages = new ArrayList<>();

        ApiErrorCodes apiErrorCodes = handlerMethod.getMethodAnnotation(ApiErrorCodes.class);
        if (apiErrorCodes != null) {
            for (ApiErrorCode annotation : apiErrorCodes.value()) {
                failMessages.addAll(Arrays.asList(annotation.value()));
            }
            return failMessages;
        }

        ApiErrorCode[] singleAnnotations = handlerMethod.getMethod().getAnnotationsByType(ApiErrorCode.class);
        for (ApiErrorCode annotation : singleAnnotations) {
            failMessages.addAll(Arrays.asList(annotation.value()));
        }

        return failMessages;
    }

    private void addDefaultErrorResponses(Operation operation, HandlerMethod handlerMethod) {
        String path = resolveRequestPath(handlerMethod);
        ApiResponses responses = operation.getResponses();

        ApiResponse response500 = new ApiResponse()
                .description(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        MediaType mediaType500 = new MediaType()
                .schema(new Schema<>().$ref("#/components/schemas/APIErrorResponse"));
        Example example500 = new Example();
        example500.setValue(Map.of(
                "code", FailMessage.INTERNAL_SERVER_ERROR.getCode(),
                "message", FailMessage.INTERNAL_SERVER_ERROR.getMessage()
        ));
        example500.setDescription(FailMessage.INTERNAL_SERVER_ERROR.name());
        mediaType500.addExamples(FailMessage.INTERNAL_SERVER_ERROR.name(), example500);
        response500.setContent(new Content().addMediaType("application/json", mediaType500));
        responses.addApiResponse("500", response500);
    }

    private String resolveRequestPath(HandlerMethod handlerMethod) {
        String classPath = "";
        RequestMapping classMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
        if (classMapping != null && classMapping.value().length > 0) {
            classPath = classMapping.value()[0];
        }

        String methodPath = "";
        GetMapping getMapping = handlerMethod.getMethodAnnotation(GetMapping.class);
        if (getMapping != null && getMapping.value().length > 0) {
            methodPath = getMapping.value()[0];
        }
        PostMapping postMapping = handlerMethod.getMethodAnnotation(PostMapping.class);
        if (postMapping != null && postMapping.value().length > 0) {
            methodPath = postMapping.value()[0];
        }
        PutMapping putMapping = handlerMethod.getMethodAnnotation(PutMapping.class);
        if (putMapping != null && putMapping.value().length > 0) {
            methodPath = putMapping.value()[0];
        }
        PatchMapping patchMapping = handlerMethod.getMethodAnnotation(PatchMapping.class);
        if (patchMapping != null && patchMapping.value().length > 0) {
            methodPath = patchMapping.value()[0];
        }
        DeleteMapping deleteMapping = handlerMethod.getMethodAnnotation(DeleteMapping.class);
        if (deleteMapping != null && deleteMapping.value().length > 0) {
            methodPath = deleteMapping.value()[0];
        }

        return classPath + methodPath;
    }
}
