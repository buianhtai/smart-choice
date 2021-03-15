package com.ms.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ms.exceptions.SmartChoiceBusinessError;
import com.ms.exceptions.SmartChoiceException;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;


@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class BaseResponse<T> {
    public static final String OK_CODE = "200";
    private T data;
    private Metadata meta = new Metadata();

    public static <T> BaseResponse<T> ofSucceeded(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.data = data;
        response.meta.code = OK_CODE;
        return response;
    }

    public static <T> BaseResponse<T> ofSucceeded() {
        BaseResponse<T> response = new BaseResponse<>();
        response.meta.code = OK_CODE;
        return response;
    }

    public static BaseResponse<Void> ofFailed(SmartChoiceBusinessError errorCode) {
        return ofFailed(errorCode, null);
    }

    public static BaseResponse<Void> ofFailed(SmartChoiceBusinessError errorCode, String message) {
        return ofFailed(errorCode, message, null);
    }

    public static BaseResponse<Void> ofFailed(SmartChoiceBusinessError errorCode, String message, List<FieldViolation> errors) {
        BaseResponse<Void> response = new BaseResponse<>();
        response.meta.code = String.valueOf(errorCode.getCode());
        response.meta.message = (message != null) ? message : errorCode.getMessage();
        response.meta.errors = errors;
        return response;
    }

    public static BaseResponse<Void> ofFailed(SmartChoiceException exception) {
        return ofFailed(exception.getErrorCode(), exception.getMessage());
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Metadata {
        private String code;
        private Integer page;
        private Integer size;
        private Long total;
        private List<FieldViolation> errors;
        private String message;
    }
}
