package br.com.todo.api.exceptions;

import br.com.todo.api.exceptions.throwables.entity.EntityException;
import br.com.todo.api.exceptions.throwables.global.UnmappedErrorException;
import br.com.todo.api.global.dto.ResponseBaseDto;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        if (ex instanceof EntityException) {
            return handleExceptionInternal(ex);
        }
        if (ex instanceof ServletException) {
            return handleServletException((ServletException) ex);
        }
        
        log.error("Unmapped error", new UnmappedErrorException(ex));
        var errors = ResponseBaseDto
                .builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal server error")
                .build();
        return new ResponseEntity<Object>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<Object> handleException(String message) {
        var errors = ResponseBaseDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .build();
        return new ResponseEntity<Object>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<Object> handleServletException(ServletException ex) {
        var message = ex.getCause().getMessage();
        return handleException(message);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex) {
        var message = ex.getMessage();
        return handleException(message);
    }
}
