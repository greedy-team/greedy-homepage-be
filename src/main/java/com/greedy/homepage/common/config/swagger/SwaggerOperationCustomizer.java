package com.greedy.homepage.common.config.swagger;

import com.greedy.homepage.common.exception.FailMessage;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class SwaggerOperationCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        List<FailMessage> failMessages = extractFailMessages(handlerMethod);

        if (failMessages.isEmpty()) {
            addDefaultErrorResponses(operation);
            return operation;
        }

        // 500 자동 추가
        if (failMessages.stream().noneMatch(f -> f.getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR)) {
            failMessages.add(FailMessage.INTERNAL_SERVER_ERROR);
        }

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

        Method method = handlerMethod.getMethod();
        Set<ApiErrorCode> annotations = AnnotatedElementUtils.findMergedRepeatableAnnotations(method,
                ApiErrorCode.class);

        for (ApiErrorCode annotation : annotations) {
            failMessages.addAll(Arrays.asList(annotation.value()));
        }

        return failMessages;
    }

    private void addDefaultErrorResponses(Operation operation) {
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
}
