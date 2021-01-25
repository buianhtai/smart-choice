package com.nab.exceptions;

import lombok.Getter;

@Getter
public class SmartChoiceException extends RuntimeException {
    private SmartChoiceBusinessError errorCode;

    public SmartChoiceException(SmartChoiceBusinessError errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public SmartChoiceException(SmartChoiceBusinessError errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SmartChoiceException(SmartChoiceBusinessError error, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = error;
    }
}
