package com.canyou.canyou.controllers;

import com.canyou.canyou.dto.DeveloperDto;
import com.canyou.canyou.entities.Developer;
import com.canyou.canyou.enums.Availability;
import com.canyou.canyou.exceptions.RessourceNotFoundException;
import com.canyou.canyou.services.DeveloperService;
import com.canyou.canyou.utils.ConstValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Set;

import static com.canyou.canyou.utils.ConstValues.ID;
import static com.canyou.canyou.utils.ConstValues.ONE;
import static com.canyou.canyou.utils.ErrorMsg.*;
import static com.canyou.canyou.utils.SharedConst.AVAILABILITY_LABEL;
import static com.canyou.canyou.utils.SharedConst.EXPERIENCE_LABEL;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeveloperController.class) // Spring Boot instantiates only the web layer rather than the whole context.& only the DevController not all @Controller annotated
class DeveloperControllerTest {

    public static final String API_PATH = "/developers";
    public static final String API_PATH_WITH_ID = API_PATH + "/" + ConstValues.ID;
    @Autowired
    MockMvc request;

    @MockBean
    DeveloperService service;

    List<DeveloperDto> developersDtoList = List.of(
            DeveloperDto.builder()
                    .fullName("Fatou Fall")
                    .role("Front End Software Engineer")
                    .yearsOfExperiences(3)
                    .availability(Availability.NOW.name())
                    .specialities(Set.of("HTML", "CSS", "JavaScript", "React"))
                    .build(),
            DeveloperDto.builder()
                    .fullName("Demba Ly")
                    .role("Devops Engineer")
                    .yearsOfExperiences(5)
                    .availability(Availability.SOON.name())
                    .specialities(Set.of("Kubernetes", "Ansible", "AWS", "Docker", "Jenkins"))
                    .build(),
            DeveloperDto.builder()
                    .fullName("Awa Sambou")
                    .role("Backend Software Engineer")
                    .yearsOfExperiences(7)
                    .availability(Availability.NOW.name())
                    .specialities(Set.of("Java", "Java EE", "Spring / SpringBoot", "MongoDB"))
                    .build()
    );

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAll() throws Exception {
        Mockito.when(service.getAll()).thenReturn(this.developersDtoList);

        ResultActions requestResult = request.perform(MockMvcRequestBuilders.get(API_PATH));

        Mockito.verify(service, Mockito.times(ONE)).getAll();
        requestResult.andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(objectMapper.writeValueAsString(developersDtoList))
        );
    }

    @Test
    void should_getAvailableDevelopers() throws Exception {
        List<DeveloperDto> expected = this.developersDtoList.stream()
                .filter(d -> d.getAvailability().equals(Availability.NOW.name()))
                .toList();
        System.out.println(expected);
        Mockito.when(service.getAvailableDev(Availability.NOW)).thenReturn(expected);

        ResultActions requestResult = request.perform(
                MockMvcRequestBuilders.get(API_PATH).param(AVAILABILITY_LABEL, Availability.NOW.name())
        );

        Mockito.verify(service, Mockito.times(ONE)).getAvailableDev(Availability.NOW);
        requestResult.andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(objectMapper.writeValueAsString(expected))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "test", "123", "1te$"})
    void should_throw_illegalValueException_when_getAvailableDevelopers(String availability) throws Exception {
        String result = """
                {"%s":"%s"}
                """.formatted(AVAILABILITY_LABEL, AVAILABILITY_ERROR_MSG);

        ResultActions requestResult = request.perform(
                MockMvcRequestBuilders.get(API_PATH).param(AVAILABILITY_LABEL, availability)
        );

        Mockito.verify(service, Mockito.never()).getAvailableDev(any());
        requestResult.andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(result)

        );
    }

    @Test
    void should_getDevelopersByExperience() throws Exception {
        List<DeveloperDto> expected = this.developersDtoList.stream()
                .filter(d -> d.getYearsOfExperiences() > 5)
                .toList();

        Mockito.when(service.getDevsByMinimumExperience(5)).thenReturn(expected);

        ResultActions requestResult = request.perform(
                MockMvcRequestBuilders.get(API_PATH).param(EXPERIENCE_LABEL, "5")
        );

        Mockito.verify(service, Mockito.times(ONE)).getDevsByMinimumExperience(5);
        requestResult.andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(objectMapper.writeValueAsString(expected))
        );
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {" ", "\t", "\n", "\r", "test", "#12", "-12"})
    void should_throw_illegalValueException_when_getDevsByExperiences(String exp) throws Exception {
        String result = """
                {"%s":"%s"}
                """.formatted(EXPERIENCE_LABEL, ILLEGAL_EXPERIENCE_MSG);

        ResultActions requestResult = request.perform(
                MockMvcRequestBuilders.get(API_PATH).param(EXPERIENCE_LABEL, exp)
        );

        Mockito.verifyNoInteractions(service);
        requestResult.andExpectAll(
                status().isBadRequest(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(result)

        );
    }

    @Test
    void should_getOne_Developer() throws Exception {
        Mockito.when(service.getOne(ConstValues.ID)).thenReturn(developersDtoList.get(ONE));

        ResultActions requestResult = request.perform(MockMvcRequestBuilders.get(API_PATH_WITH_ID));
        Mockito.verify(service, Mockito.times(ONE)).getOne(ConstValues.ID);
        requestResult.andExpectAll(
                status().isOk(),
                content().json(objectMapper.writeValueAsString(developersDtoList.get(ONE)))
        );
    }

    @Test
    void should_throw_RessourceNotFoundException_when_getOneDevelopers() throws Exception {
        String result = """
                {"error":"%s"}
                """.formatted(String.format(RESSOURCE_NOT_FOUND_MSG,Developer.class.getSimpleName(),ID));

        Mockito.when(service.getOne(ID)).thenThrow(new RessourceNotFoundException(Developer.class.getSimpleName(), ID));

        ResultActions requestResult = request.perform(
                MockMvcRequestBuilders.get(API_PATH_WITH_ID)
        );


        Mockito.verify(service, Mockito.times(ONE)).getOne(ID);
        requestResult.andExpectAll(
                status().isNotFound(),
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(result)

        );
    }

    @Test
    void should_saveOne_Developer() throws Exception {
        DeveloperDto developer = developersDtoList.get(ONE);
        Mockito.when(service.saveOne(developer)).thenReturn(developersDtoList.get(ONE));

        ResultActions requestResult = request.perform(
                MockMvcRequestBuilders.post(API_PATH)
                        .content(objectMapper.writeValueAsString(developer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        Mockito.verify(service, Mockito.times(ONE)).saveOne(developer);
        requestResult.andExpectAll(
                status().isCreated(),
                content().json(objectMapper.writeValueAsString(developersDtoList.get(ONE)))
        );
    }

    @Test
    void should_putOne_Developer() throws Exception {
        DeveloperDto developerDto = developersDtoList.get(ONE);
        DeveloperDto expected = developersDtoList.get(ONE);
        expected.setId(ConstValues.ID);

        Mockito.when(service.modifyOne(ConstValues.ID, developerDto)).thenReturn(expected);

        ResultActions requestResult = request.perform(
                MockMvcRequestBuilders.put(API_PATH_WITH_ID)
                        .content(objectMapper.writeValueAsString(developerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        Mockito.verify(service, Mockito.times(ONE)).modifyOne(ConstValues.ID, developerDto);
        requestResult.andExpectAll(
                status().isAccepted(),
                content().json(objectMapper.writeValueAsString(expected))
        );
    }

    @Test
    void should_deleteOne_Developer() throws Exception {
        Mockito.doNothing().when(service).deleteOne(ConstValues.ID);

        ResultActions requestResult = request.perform(MockMvcRequestBuilders.delete(API_PATH_WITH_ID));

        Mockito.verify(service, Mockito.times(ONE)).deleteOne(ConstValues.ID);
        requestResult.andExpectAll(
                status().isOk(),
                content().string(StringUtils.EMPTY)
        );
    }
}