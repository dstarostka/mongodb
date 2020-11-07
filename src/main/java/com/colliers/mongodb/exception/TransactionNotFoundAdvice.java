package com.colliers.mongodb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class TransactionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    Message transactionNotFoundHandler(TransactionNotFoundException ex) {

        return  Message.builder()
                .timestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .status(200)
                .exception("TransactionNotFoundException")
                .message(ex.getMessage())
                .build();
    }
}