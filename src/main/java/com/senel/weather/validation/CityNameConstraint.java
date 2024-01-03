package com.senel.weather.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CityNameValidator.class)
@Documented
public @interface CityNameConstraint {

    String message() default "Bitte geben Sie einen korrekten Städtenamen an";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
