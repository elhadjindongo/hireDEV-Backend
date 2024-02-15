package com.canyou.canyou.dto.mapper;

import com.canyou.canyou.dto.DeveloperDto;
import com.canyou.canyou.entities.Developer;
import com.canyou.canyou.utils.ConstValues;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class) // which is used to integrate TestContext Framework into JUnit5
//@ContextConfiguration(classes = {
//        DeveloperMapperImpl.class
//}) IS THE SAME AS BELOW
@SpringJUnitConfig(DeveloperMapperImpl.class)
class DeveloperMapperTest {
    @Autowired
    DeveloperMapper mapper;
    DeveloperDto dto;
    Developer entity;


    @Test
    void toEntity() {
        dto = DeveloperDto.builder()
                .id(ConstValues.ID)
                .fullName(ConstValues.FULL_NAME)
                .availability(ConstValues.AVAILABILITY.name())
                .role(ConstValues.JAVA_DEVELOPER)
                .specialities(ConstValues.SPECIALITIES)
                .yearsOfExperiences(ConstValues.YEARS_OF_EXPERIENCES)
                .build();
        entity = mapper.toEntity(dto);
        performAssertion();

    }

    @Test
    void toDto() {
        entity = Developer.builder()
                .id(ConstValues.ID)
                .fullName(ConstValues.FULL_NAME)
                .availability(ConstValues.AVAILABILITY)
                .role(ConstValues.JAVA_DEVELOPER)
                .specialities(ConstValues.SPECIALITIES)
                .yearsOfExperiences(ConstValues.YEARS_OF_EXPERIENCES)
                .build();
        dto = mapper.toDto(entity);
        performAssertion();
    }

    private void performAssertion() {
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getFullName(), entity.getFullName());
        assertEquals(dto.getAvailability(), entity.getAvailability().toString());
        assertEquals(dto.getSpecialities(), entity.getSpecialities());
        assertEquals(dto.getYearsOfExperiences(), entity.getYearsOfExperiences());
    }
}