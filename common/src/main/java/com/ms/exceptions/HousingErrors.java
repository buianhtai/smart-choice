package com.ms.exceptions;

import org.springframework.http.HttpStatus;


public class HousingErrors {

    /**
     * 400
     */

    public static final SmartChoiceBusinessError INVALID_INPUT_ERROR = new SmartChoiceBusinessError(400000, "Invalid input error", HttpStatus.BAD_REQUEST);


    /**
     * 404
     */
    public static final SmartChoiceBusinessError NOT_FOUND_ERROR = new SmartChoiceBusinessError(400001, "Resource not found", HttpStatus.NOT_FOUND);


    private HousingErrors() {
    }
}
