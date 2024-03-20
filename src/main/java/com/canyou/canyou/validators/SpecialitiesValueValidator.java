/* Created by El Hadji M. NDONGO  */
/* on 19 03 2024 */
/* Project: can-you */

package com.canyou.canyou.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class SpecialitiesValueValidator implements ConstraintValidator<AcceptableValueForSpecialities, Set<String>> {

    int min;

    @Override
    public void initialize(AcceptableValueForSpecialities constraintAnnotation) {
        min = constraintAnnotation.min();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Should validate that specialities is not null, contains at least one element and elements inside are not empty
     */
    @Override
    public boolean isValid(Set<String> specialities, ConstraintValidatorContext constraintValidatorContext) {

        return null != specialities && specialities.size() >= min && specialities.stream().filter(String::isEmpty).toList().isEmpty();
    }
}
