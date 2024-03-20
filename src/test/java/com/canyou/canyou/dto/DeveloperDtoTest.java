package com.canyou.canyou.dto;

import com.canyou.canyou.utils.ConstValues;
import com.canyou.canyou.utils.ErrorMsg;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // JUnit 5
class DeveloperDtoTest {
    DeveloperDto underTest;
    Validator validator;


    final String CONSTRAINT_VIOLATION_NOT_EXIST = "There should NOT HAVE constraints violations !!!";

    @BeforeEach
    void setUp() {
        underTest = DeveloperDto.builder()
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
        var constraintViolations = validator.validate(underTest);
        System.out.println(constraintViolations);
        assertTrue(constraintViolations.isEmpty(), CONSTRAINT_VIOLATION_NOT_EXIST);

    }

    @Test
    void all_field_are_empty() {
        underTest = new DeveloperDto();
        var constraintViolations = validator.validate(underTest);
        assertFalse(constraintViolations.isEmpty());
    }


    @ParameterizedTest(name = "{index} => fullName_empty_null_space({0})")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "\r"})
    void fullName_empty_null_space(String fullName) {
        underTest.setFullName(fullName);
        this.assertConstraintViolationExist();
    }

    @ParameterizedTest(name = "{index} => fullName_containBadValue({0})")
    @ValueSource(strings = {"123", "!2", "elhadji1", "el/hadji", "elh@dji"})
    void fullName_containBadValue(String fullName) {

        underTest.setFullName(fullName);
        this.assertConstraintViolationExist(ErrorMsg.CAN_NOT_CONTAIN_SPECIAL_CHAR_ERROR_MSG);
    }

    @ParameterizedTest(name = "{index} => availability_empty({0})")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "\r"})
    void role_empty(String role) {
        underTest.setRole(role);
        this.assertConstraintViolationExist();
    }

    @ParameterizedTest(name = "{index} => role_containBadValue({0})")
    @ValueSource(strings = {"123&", "!2", "soft@engineer"})
    void role_containBadValue(String role) {
        underTest.setRole(role);
        this.assertConstraintViolationExist(ErrorMsg.CAN_NOT_CONTAIN_SPECIAL_CHAR_AND_DIGIT_ERROR_MSG);
    }


    @ParameterizedTest
    @ValueSource(ints = {-1, 100})
    void yearsOfExperiences_containBadValue(int number) {
        underTest.setYearsOfExperiences(number);
        this.assertConstraintViolationExist(ErrorMsg.YEARS_OF_EXPERIENCE_ERROR_MSG);
    }


    @ParameterizedTest(name = "{index} => availability_containBadValue({0})")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "\r", "yes", "123", "yup123"})
    void availability_containBadValue(String availability) {
        underTest.setAvailability(availability);
        this.assertConstraintViolationExist(ErrorMsg.AVAILABILITY_ERROR_MSG);
    }

    @Test
    void specialities_empty() {
        fail("Specialities validation should be implemented");
    }

    @Test
    void specialities_containBadValue() {
        fail("Specialities validation should be implemented");
    }


//    private void assertConstraintViolationExist() {
//        var constraintViolations = validator.validate(underTest);
//        assertFalse(constraintViolations.isEmpty(), "There SHOULD HAVE constraints violations !!!");
//        assertEquals(constraintViolations.size(), 1);
//    }

    private void assertConstraintViolationExist() {
        var constraintViolations = validator.validate(underTest);
        assertFalse(constraintViolations.isEmpty(), this.CONSTRAINT_VIOLATION_NOT_EXIST);
    }
    private void assertConstraintViolationExist(String expectedMessage) {
        var constraintViolations = validator.validate(underTest);
        System.out.println("--------");
        constraintViolations.forEach(System.out::println);
        assertEquals(1, constraintViolations.size());

        Optional<ConstraintViolation<DeveloperDto>> first = constraintViolations.stream().findFirst();
        if (first.isPresent()) {
            ConstraintViolation<DeveloperDto> error = first.get();
            assertEquals(expectedMessage, error.getMessage());
        } else {
            fail(CONSTRAINT_VIOLATION_NOT_EXIST);
        }
    }

}