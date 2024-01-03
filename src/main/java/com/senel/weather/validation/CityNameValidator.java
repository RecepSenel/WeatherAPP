package com.senel.weather.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CityNameValidator implements ConstraintValidator<CityNameConstraint, String> {

    @Override
    public void initialize(CityNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.isAlpha(value);
    }
}
