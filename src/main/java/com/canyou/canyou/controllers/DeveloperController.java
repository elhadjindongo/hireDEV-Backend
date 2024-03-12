/* Created by El Hadji M. NDONGO  */
/* on 16 01 2024 */
/* Project: can-you */

package com.canyou.canyou.controllers;

import com.canyou.canyou.dto.DeveloperDto;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.canyou.canyou.utils.ErrorMsg.AVAILABILITY_ERROR_MSG;
import static com.canyou.canyou.utils.ErrorMsg.ILLEGAL_EXPERIENCE_MSG;
import static com.canyou.canyou.utils.SharedConst.AVAILABILITY_LABEL;
import static com.canyou.canyou.utils.SharedConst.EXPERIENCE_LABEL;

@RestController
@RequestMapping("/developers")
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @Operation(summary = "get all Developers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DeveloperDto.class)))),
            @ApiResponse(responseCode = "400", description = "When param is malformed",
                    content = @Content(examples = @ExampleObject(value = ObjectDataExemple.BAD_REQUEST)))
    })
    @GetMapping
    public List<DeveloperDto> getAll(@RequestParam(required = false) String availability,
                                     @RequestParam(required = false) String experience) {
        if (null != availability) {
            return getAvailableDevs(availability);
        } else if (null != experience) {
            try {
                int yearOfExperience = Integer.parseInt(experience);
                if (yearOfExperience < 0) {
                    throw new Exception();
                }
                return getDevsByMinimumExperience(yearOfExperience);
            } catch (Exception e) {
                throw new IllegalValueException(ILLEGAL_EXPERIENCE_MSG, EXPERIENCE_LABEL);
            }
        } else {
            return developerService.getAll();
        }
    }

    private List<DeveloperDto> getDevsByMinimumExperience(int yearsOfExperience) {
        return developerService.getDevsByMinimumExperience(yearsOfExperience);
    }

    //getting dev that are available or not
    private List<DeveloperDto> getAvailableDevs(String availability) {
        Availability availabilityValue = null;
        try {
            availabilityValue = Availability.valueOf(availability.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(AVAILABILITY_ERROR_MSG, AVAILABILITY_LABEL);
        }

        return developerService.getAvailableDev(availabilityValue);
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
            @Content(examples = @ExampleObject(value = ObjectDataExemple.SAVE_EXEMPLE)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ObjectDataExemple.SAVE_EXEMPLE)
                    )),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<DeveloperDto> saveOne(@Valid @RequestBody DeveloperDto developer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(developerService.saveOne(developer));
    }

    @Operation(summary = "modify a Developer by its id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(examples = @ExampleObject(value = ObjectDataExemple.EDIT_EXEMPLE))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = ObjectDataExemple.EDIT_EXEMPLE))),
            @ApiResponse(responseCode = "404", description = "Developer not found",
                    content = @Content(examples = @ExampleObject(value = ObjectDataExemple.NOT_FOUND)))})
    @PutMapping("/{id}")
    public ResponseEntity<DeveloperDto> putOne(@PathVariable Long id, @Valid @RequestBody DeveloperDto developer) {
        return ResponseEntity.accepted().body(developerService.modifyOne(id, developer));
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
