package com.canyou.canyou.services;

import com.canyou.canyou.dto.DeveloperDto;
import com.canyou.canyou.dto.mapper.DeveloperMapper;
import com.canyou.canyou.entities.Developer;
import com.canyou.canyou.exceptions.RessourceNotFoundException;
import com.canyou.canyou.repositories.DeveloperRepository;
import com.canyou.canyou.utils.ConstValues;
import com.canyou.canyou.utils.ErrorMsg;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.logging.LogLevel;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DeveloperServiceImplTest {
    public static final int ZERO = 0;
    public static final int WANTED_NUMBER_OF_INVOCATIONS = 1;
    @Mock
    DeveloperRepository repository;
    @Mock
    DeveloperMapper mapper;
    List<DeveloperDto> devsDTO;
    List<Developer> devsEntity;

    @InjectMocks
    DeveloperServiceImpl service;

    @BeforeEach
    void setUp() {
        devsDTO = List.of(
                DeveloperDto.builder()
                        .id(ConstValues.ID)
                        .fullName(ConstValues.FULL_NAME)
                        .availability(ConstValues.AVAILABILITY.name())
                        .role(ConstValues.JAVA_DEVELOPER)
                        .specialities(ConstValues.SPECIALITIES)
                        .yearsOfExperiences(ConstValues.YEARS_OF_EXPERIENCES)
                        .build(),
                DeveloperDto.builder()
                        .id(ConstValues.ID)
                        .fullName(ConstValues.FULL_NAME_1)
                        .availability(ConstValues.AVAILABILITY_1.name())
                        .role(ConstValues.JS_DEVELOPER)
                        .specialities(ConstValues.SPECIALITIES_1)
                        .yearsOfExperiences(ConstValues.YEARS_OF_EXPERIENCES_1)
                        .build(),
                DeveloperDto.builder()
                        .id(ConstValues.ID_2)
                        .fullName(ConstValues.FULL_NAME_2)
                        .availability(ConstValues.AVAILABILITY.name())
                        .role(ConstValues.PYTHON_DEVELOPER)
                        .specialities(ConstValues.SPECIALITIES_2)
                        .yearsOfExperiences(ConstValues.YEARS_OF_EXPERIENCES_2)
                        .build()
        );
        devsEntity = List.of(
                Developer.builder()
                        .id(ConstValues.ID)
                        .fullName(ConstValues.FULL_NAME)
                        .availability(ConstValues.AVAILABILITY)
                        .role(ConstValues.JAVA_DEVELOPER)
                        .specialities(ConstValues.SPECIALITIES)
                        .yearsOfExperiences(ConstValues.YEARS_OF_EXPERIENCES)
                        .build(),
                Developer.builder()
                        .id(ConstValues.ID_1)
                        .fullName(ConstValues.FULL_NAME_1)
                        .availability(ConstValues.AVAILABILITY_1)
                        .role(ConstValues.JS_DEVELOPER)
                        .specialities(ConstValues.SPECIALITIES_1)
                        .yearsOfExperiences(ConstValues.YEARS_OF_EXPERIENCES_1)
                        .build(),
                Developer.builder()
                        .id(ConstValues.ID_2)
                        .fullName(ConstValues.FULL_NAME_2)
                        .availability(ConstValues.AVAILABILITY)
                        .role(ConstValues.PYTHON_DEVELOPER)
                        .specialities(ConstValues.SPECIALITIES_2)
                        .yearsOfExperiences(ConstValues.YEARS_OF_EXPERIENCES_2)
                        .build()
        );
    }

    @Test
    void getAll() {
        //when
        Mockito.when(repository.findAll()).thenReturn(devsEntity);
        IntStream.range(ZERO, devsEntity.size())
                .forEach(i -> Mockito.when(mapper.toDto(devsEntity.get(i))).thenReturn(devsDTO.get(i)));
        //perform
        List<DeveloperDto> result = service.getAll();
        //assert
        Mockito.verify(repository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findAll();
        assertEquals(result.size(), devsDTO.size());
        IntStream.range(ZERO, result.size()).forEach(i -> {
            assertOneDev(result.get(i), i);
        });

    }

    @Test
    void getOne() {
        //when
        Mockito.when(repository.findById(ConstValues.ID)).thenReturn(Optional.of(this.devsEntity.get(ZERO)));
        Mockito.when(mapper.toDto(this.devsEntity.get(ZERO))).thenReturn(this.devsDTO.get(ZERO));
        //call
        DeveloperDto result = service.getOne(ConstValues.ID);
        //assert
        Mockito.verify(repository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findById(ConstValues.ID);
        assertOneDev(result, ZERO);
    }

    @Test
    void getOne_NotExist() {
        //when
        Mockito.when(repository.findById(ConstValues.ID)).thenThrow(new RessourceNotFoundException(Developer.class.getSimpleName(), ConstValues.ID));
        //assert
        try {
            service.getOne(ConstValues.ID);
            fail("There should throw a RessourceNotFoundException");
        } catch (Exception e) {
            Mockito.verify(repository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findById(ConstValues.ID);
            assertTrue(e instanceof RessourceNotFoundException);
            assertEquals(String.format(ErrorMsg.RESSOURCE_NOT_FOUND_MSG, Developer.class.getSimpleName(), ConstValues.ID), e.getMessage());
        }
    }

    @Test
    void saveOne() {
        //when
        DeveloperDto toSave = SerializationUtils.clone(this.devsDTO.get(ZERO));
        toSave.setId(null);
        Mockito.when(repository.save(this.devsEntity.get(ZERO))).thenReturn(this.devsEntity.get(ZERO));
        Mockito.when(mapper.toDto(this.devsEntity.get(ZERO))).thenReturn(this.devsDTO.get(ZERO));
        Mockito.when(mapper.toEntity(toSave)).thenReturn(this.devsEntity.get(ZERO));
        //call
        DeveloperDto result = service.saveOne(toSave);
        //assert
        Mockito.verify(repository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).save(this.devsEntity.get(ZERO));
        assertOneDev(result, ZERO);
    }

    @Test
    void modifyOne() {
        //when
        DeveloperDto toSave = SerializationUtils.clone(this.devsDTO.get(ZERO));
        toSave.setId(null);
        Mockito.when(repository.findById(ConstValues.ID)).thenReturn(Optional.ofNullable(this.devsEntity.get(ZERO)));
        Mockito.when(repository.save(this.devsEntity.get(ZERO))).thenReturn(this.devsEntity.get(ZERO));
        Mockito.when(mapper.toDto(this.devsEntity.get(ZERO))).thenReturn(this.devsDTO.get(ZERO));
        Mockito.when(mapper.toEntity(toSave)).thenReturn(this.devsEntity.get(ZERO));
        //call
        DeveloperDto result = service.modifyOne(ConstValues.ID, toSave);
        //assert
        Mockito.verify(repository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findById(ConstValues.ID);
        Mockito.verify(repository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).save(this.devsEntity.get(ZERO));
        assertOneDev(result, ZERO);
    }

    @Test
    void getDevsByMinimunExperience() {
    }

    @Test
    void getAvailableDev() {
    }

    @Test
    void getDevBySpecialities() {
    }

    @Test
    void deleteOne() {
    }

    private void assertOneDev(DeveloperDto result, int zero) {
        assertEquals(result.getId(), devsDTO.get(zero).getId());
        assertEquals(result.getFullName(), devsDTO.get(zero).getFullName());
        assertEquals(result.getRole(), devsDTO.get(zero).getRole());
        assertEquals(result.getAvailability(), devsDTO.get(zero).getAvailability());
        assertEquals(result.getSpecialities(), devsDTO.get(zero).getSpecialities());
        assertEquals(result.getYearsOfExperiences(), devsDTO.get(zero).getYearsOfExperiences());
    }
}