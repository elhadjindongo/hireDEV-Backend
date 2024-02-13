/* Created by El Hadji M. NDONGO  */
/* on 16 01 2024 */
/* Project: can-you */

package com.canyou.canyou.entities;


import com.canyou.canyou.enums.Availability;
import com.canyou.canyou.utils.ErrorMsg;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Developer implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = ErrorMsg.CAN_NOT_BE_BLANK_ERROR_MSG)
    @Column(nullable = false)
    private String fullName;
    @NotBlank(message = ErrorMsg.ROLE_ERROR_MSG)
    @Column(nullable = false)
    private String role;
    @Column(length = 2, nullable = false)
    private int yearsOfExperiences;
    @Enumerated(EnumType.STRING)
    private Availability availability;
    private Set<String> specialities;
}
