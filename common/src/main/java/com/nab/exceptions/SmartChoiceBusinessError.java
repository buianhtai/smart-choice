package com.nab.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class SmartChoiceBusinessError {
    private int code;
    private String message;
    private HttpStatus httpStatus;

    public SmartChoiceBusinessError(String resource){
        this.code = 404;
        this.message = resource +" is not found";
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
