/* Created by El Hadji M. NDONGO  */
/* on 02 02 2024 */
/* Project: can-you */

package com.canyou.canyou.dto;


import com.canyou.canyou.utils.ErrorMsg;
import com.canyou.canyou.utils.SharedConst;
import com.canyou.canyou.validators.AcceptableValueForExperience;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class DeveloperDto implements Serializable {
    private Long id;

    @NotBlank(message = ErrorMsg.FULL_NAME_ERROR_MSG)
    @Pattern(regexp = SharedConst.NAME_REGEX, message = ErrorMsg.CAN_NOT_CONTAIN_SPECIAL_CHAR_ERROR_MSG)
    private String fullName;

    @Pattern(regexp = SharedConst.ROLE_REGEX, message = ErrorMsg.CAN_NOT_CONTAIN_SPECIAL_CHAR_AND_DIGIT_ERROR_MSG)
    @NotBlank(message = ErrorMsg.ROLE_ERROR_MSG)
    private String role;

    @AcceptableValueForExperience
    private Integer yearsOfExperiences;

    @Pattern(regexp = SharedConst.AVAILABILITY_REGEX, message = ErrorMsg.AVAILABILITY_ERROR_MSG)
    @NotNull(message = ErrorMsg.AVAILABILITY_ERROR_MSG)
    private String availability;

    //    @NotBlank(message = ErrorMsg.SPECIALITY_ERROR_MSG) cannot be null or empty
    private Set<String> specialities;
}
