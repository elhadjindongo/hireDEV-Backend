/* Created by El Hadji M. NDONGO  */
/* on 16 01 2024 */
/* Project: can-you */

package com.canyou.canyou.controllers;

import com.canyou.canyou.dto.DeveloperDto;
import com.canyou.canyou.dto.SpecialitiesDto;
import com.canyou.canyou.enums.Availability;
import com.canyou.canyou.exceptions.IllegalValueException;
import com.canyou.canyou.services.DeveloperService;
import com.canyou.canyou.utils.ObjectDataExemple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.examples.Example;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.canyou.canyou.utils.ErrorMsg.AVAILABILITY_ERROR_MSG;
import static com.canyou.canyou.utils.ErrorMsg.ILLEGAL_EXPERIENCE_MSG;

@RestController
@RequestMapping("/developers")
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;
    private static final String EXPERIENCE_LABEL = "experience";
    private static final String AVAILABILITY_LABEL = "availability";

    @Operation(summary = "get all Developers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DeveloperDto.class)))),
    })
    @GetMapping
    public List<DeveloperDto> getAll(@RequestBody(required = false) SpecialitiesDto specialitiesDto,
                                     @RequestParam(required = false) String availability,
                                     @RequestParam(required = false) String experience) {
        if (null != availability) {
            return getAvailableDevs(availability);
        } else if (null != experience) {
            int yearOfExperience = 0;
            try {
                yearOfExperience = Integer.parseInt(experience);
            } catch (NumberFormatException e) {
                throw new IllegalValueException(ILLEGAL_EXPERIENCE_MSG, EXPERIENCE_LABEL);
            }
            return getDevsByMinimumExperience(yearOfExperience);
        } else if (null != specialitiesDto) {
            return this.getBySpecialities(specialitiesDto);
        } else {
            return developerService.getAll();
        }
    }

    private List<DeveloperDto> getDevsByMinimumExperience(int yearsOfExperience) {
        return developerService.getDevsByMinimunExperience(yearsOfExperience);
    }

    //getting dev that are available or not
    public List<DeveloperDto> getAvailableDevs(String availability) {
        Availability availabilityValue = null;
        try {
            availabilityValue = Availability.valueOf(availability.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(AVAILABILITY_ERROR_MSG, AVAILABILITY_LABEL);
        }

        return developerService.getAvailableDev(availabilityValue);
    }

    //todo: get by specialities is buggy
    public List<DeveloperDto> getBySpecialities(SpecialitiesDto specialitiesDto) {
        return this.developerService.getDevBySpecialities(specialitiesDto);
    }

    @Operation(summary = "get one Developer by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DeveloperDto.class))),
            @ApiResponse(responseCode = "404", description = "Developer not found",
                    content = @Content(examples = @ExampleObject(value = ObjectDataExemple.NOT_FOUND)))})

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(developerService.getOne(id));
    }

    @Operation(summary = "save one Developer ",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(examples = @ExampleObject(value = ObjectDataExemple.IMPUTE_EXEMPLE)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = DeveloperDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)})
    @PostMapping
    public DeveloperDto saveOne(@Valid @RequestBody DeveloperDto developer) {
        return developerService.saveOne(developer);
    }

    @Operation(summary = "modify a Developer by its id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(examples = @ExampleObject(value = ObjectDataExemple.IMPUTE_EXEMPLE))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeveloperDto.class))),
            @ApiResponse(responseCode = "404", description = "Developer not found",
                    content = @Content(examples = @ExampleObject(value = ObjectDataExemple.NOT_FOUND)))})
    @PutMapping("/{id}")
    public DeveloperDto putOne(@PathVariable Long id, @RequestBody DeveloperDto developer) {
        return developerService.modifyOne(id, developer);
    }

    @Operation(summary = "Delete a Developer by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer successfully deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Developer not found",
                    content = @Content(examples = @ExampleObject(value = ObjectDataExemple.NOT_FOUND)))})
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        developerService.deleteOne(id);
    }
}
