package com.sengul.biddingapinew.application.exception.handler;

import com.sengul.biddingapinew.application.exception.NotFoundException;
import com.sengul.biddingapinew.application.response.Response;
import com.sengul.biddingapinew.application.response.builder.ResponseBuilder;
import com.sengul.biddingapinew.application.response.builder.ResponseEntityBuilder;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Response<Object>> handleNotFoundException(NotFoundException ex) {
        return handleExceptionWithoutStackTrace(ex);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Response<StackTraceElement[]>> handleRequestValidationException(ValidationException ex) {
        return handleExceptionWithStackTrace(ex);
    }

    private ResponseEntity<Response<StackTraceElement[]>> handleExceptionWithStackTrace(Exception ex) {
        Response<StackTraceElement[]> response = new ResponseBuilder<StackTraceElement[]>()
                .withMessage(ex.getMessage())
                .withData(ex.getStackTrace())
                .build();
        return new ResponseEntityBuilder<Response<StackTraceElement[]>>()
                .withResponse(response)
                .build();
    }

    private ResponseEntity<Response<Object>> handleExceptionWithoutStackTrace(Exception ex) {
        Response<Object> response = new ResponseBuilder<>()
                .withMessage(ex.getMessage())
                .withData(null)
                .build();
        return new ResponseEntityBuilder<Response<Object>>()
                .withResponse(response)
                .withStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}