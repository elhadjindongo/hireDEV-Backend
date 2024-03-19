/* Created by El Hadji M. NDONGO  */
/* on 19 03 2024 */
/* Project: can-you */

package com.canyou.canyou.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExperienceValueValidator implements ConstraintValidator<AcceptableValueForExperience, Integer> {

    int max;
    int min;

    @Override
    public void initialize(AcceptableValueForExperience constraintAnnotation) {
        max = constraintAnnotation.max();
        min = constraintAnnotation.min();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer experienceValue, ConstraintValidatorContext ctx) {

        return null != experienceValue && experienceValue >= min && experienceValue < max;
    }
}
