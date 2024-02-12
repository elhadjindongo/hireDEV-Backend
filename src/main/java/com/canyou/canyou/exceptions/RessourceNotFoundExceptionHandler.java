/* Created by El Hadji M. NDONGO  */
/* on 22 01 2024 */
/* Project: can-you */

package com.canyou.canyou.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RessourceNotFoundExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(RessourceNotFoundException.class)
    Map<String, String> notFoundExceptionHandler(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }
}