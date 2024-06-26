package ada.tech.fornecedor.controllers;

import ada.tech.fornecedor.domain.dto.ErrorResponse;
import ada.tech.fornecedor.domain.dto.exceptions.AlreadyExistsException;
import ada.tech.fornecedor.domain.dto.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.createFromException(exception));
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(final AlreadyExistsException exception) {
//        ErrorResponse errorResponse = ErrorResponse.createFromException(exception);
//        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.createFromException(exception));
    }
}
