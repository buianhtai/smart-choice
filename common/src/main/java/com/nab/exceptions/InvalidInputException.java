package com.nab.exceptions;

public class InvalidInputException extends SmartChoiceException {

    public InvalidInputException() {
        super(HousingErrors.INVALID_INPUT_ERROR);
    }

    public InvalidInputException(String message) {
        super(HousingErrors.INVALID_INPUT_ERROR, message);
    }
}
