package com.greedy.homepage.common.config.swagger;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class NullableSchemaCustomizer implements PropertyCustomizer {

    @Override
    @SuppressWarnings("rawtypes")
    public io.swagger.v3.oas.models.media.Schema customize(
            io.swagger.v3.oas.models.media.Schema property,
            AnnotatedType type
    ) {
        Annotation[] annotations = type.getCtxAnnotations();
        if (annotations == null) {
            return property;
        }

        for (Annotation annotation : annotations) {
            if (annotation instanceof Schema schemaAnnotation) {
                if (schemaAnnotation.nullable()) {
                    property.setNullable(true);
                }
            }
        }

        return property;
    }
}
