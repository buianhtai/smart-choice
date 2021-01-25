package com.nab.http;

import java.nio.file.AccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.nab.exceptions.InvalidInputException;
import com.nab.exceptions.NotFoundException;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
class ErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody
    HttpErrorInfo handleNotFoundExceptions(ServerHttpRequest request, Exception ex) {

        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody HttpErrorInfo handleInvalidInputException(ServerHttpRequest request, Exception ex) {

        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {
        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();

        LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Mono<ResponseEntity<String>> handle(AccessDeniedException ex) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error(ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<String>> handle(RuntimeException ex) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error(ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handle(Exception ex) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error(ex.getMessage());
        return Mono.just(ResponseEntity.badRequest().build());
    }
}
