/* Created by El Hadji M. NDONGO  */
/* on 3/19/2024                     */
/* Project: can-you       */

package com.canyou.canyou.validators;


import com.canyou.canyou.utils.ErrorMsg;
import com.canyou.canyou.utils.SharedConst;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = SpecialitiesValueValidator.class)
public @interface AcceptableValueForSpecialities {
    int min() default SharedConst.MIN_SPECIALITIES_VALUE;

    String message() default ErrorMsg.SPECIALITY_ERROR_MSG;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
