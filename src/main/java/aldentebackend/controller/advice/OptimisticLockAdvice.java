package aldentebackend.controller.advice;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OptimisticLockAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {OptimisticLockingFailureException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, HttpStatus.CONFLICT, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}