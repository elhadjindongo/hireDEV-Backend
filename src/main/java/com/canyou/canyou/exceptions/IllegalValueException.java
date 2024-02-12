/* Created by El Hadji M. NDONGO  */
/* on 01 02 2024 */
/* Project: can-you */

package com.canyou.canyou.exceptions;

import lombok.Getter;

@Getter
public class IllegalValueException extends RuntimeException {

    private final String field;
    public IllegalValueException(String message,String field) {
            super(message);
            this.field = field;
        }
}
