package com.ademozalp.yemek_burada.exception;

import com.ademozalp.yemek_burada.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(YemekBuradaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse handleYemekBuradaException(YemekBuradaException e) {
        return new GenericResponse(e.getMessage(), 400, null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new GenericResponse(e.getMessage(), 400, null);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        HashMap<String, String> fieldErrorsMap = new HashMap<>();

        List<FieldError> fieldErrors = ex.getFieldErrors();

        fieldErrors.forEach(fieldError -> fieldErrorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return new GenericResponse("VALIDATION EXCEPTION", 400, fieldErrorsMap);
    }


}
