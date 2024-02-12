package com.canyou.canyou.dto;

import com.canyou.canyou.utils.ConstValues;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;



import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // JUnit 5
class DeveloperDtoTest {
    DeveloperDto developerDto;
    Validator validator;

    @BeforeEach
    void setUp() {
        developerDto = DeveloperDto.builder()
                .id(ConstValues.ID)
                .fullName(ConstValues.FULL_NAME)
                .availability(ConstValues.AVAILABILITY.name())
                .role(ConstValues.JAVA_DEVELOPER)
                .specialities(ConstValues.SPECIALITIES)
                .yearsOfExperiences(ConstValues.YEARS_OF_EXPERIENCES)
                .build();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    void no_constraintsViolation() {
        var constraintViolations = validator.validate(developerDto);
        System.out.println(constraintViolations);
        String CONSTRAINT_VIOLATION_NOT_EXIST = "There should NOT HAVE constraints violations !!!";
        assertTrue(constraintViolations.isEmpty(), CONSTRAINT_VIOLATION_NOT_EXIST);

    }

    @Test
    void all_field_are_empty() {
        developerDto = new DeveloperDto();
        this.assertConstraintViolationExist();
    }


    @ParameterizedTest(name = "{index} => fullName_empty_null_space({0})")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "\r"})
    void fullName_empty_null_space(String fullName) {
        developerDto.setFullName(fullName);
        this.assertConstraintViolationExist();
    }

    @ParameterizedTest(name = "{index} => fullName_containBadValue({0})")
    @ValueSource(strings = {"123", "!2", "elhadji1", "el/hadji", "@elhadji"})
    void fullName_containBadValue(String fullName) {

        developerDto.setFullName(fullName);
        this.assertConstraintViolationExist();
    }

    @ParameterizedTest(name = "{index} => availability_empty({0})")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "\r"})
    void role_empty(String role) {
        developerDto.setRole(role);
        this.assertConstraintViolationExist();
    }

    @ParameterizedTest(name = "{index} => role_containBadValue({0})")
    @ValueSource(strings = {"123&", "!2", "soft@engineer"})
    void role_containBadValue(String role) {
        developerDto.setRole(role);
        this.assertConstraintViolationExist();
    }


    @Test
    void yearsOfExperiences_containBadValue() {
        fail("Specialities validation should be implemented");
    }


    @ParameterizedTest(name = "{index} => availability_containBadValue({0})")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "\r", "yes", "123", "yup123"})
    void availability_containBadValue(String availability) {
        developerDto.setAvailability(availability);
        this.assertConstraintViolationExist();
    }

    @Test
    void specialities_empty() {
        fail("Specialities validation should be implemented");
    }

    @Test
    void specialities_containBadValue() {
        fail("Specialities validation should be implemented");
    }


    private void assertConstraintViolationExist() {
        var constraintViolations = validator.validate(developerDto);
        String CONSTRAINT_VIOLATION_EXISTS = "There SHOULD HAVE constraints violations !!!";
        assertFalse(constraintViolations.isEmpty(), CONSTRAINT_VIOLATION_EXISTS);
    }

}