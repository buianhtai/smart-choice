package com.ms.exceptions;

public class NotFoundException extends SmartChoiceException {

    public NotFoundException() {
        super(HousingErrors.NOT_FOUND_ERROR);
    }

    public NotFoundException(String message) {
        super(HousingErrors.NOT_FOUND_ERROR, message);
    }
}
