package br.com.zup.academy.benzaquem.shared;


import br.com.zup.academy.benzaquem.shared.message.ValidatorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class SharedExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        FieldError fe = ex.getBindingResult().getFieldError("documento");
        if (fe != null && fe.getCode().equals("CampoUnico"))
            return ResponseEntity.unprocessableEntity().build();
//        ValidatorMessageResponse errorResponse = new ValidatorMessageResponse(HttpStatus.BAD_REQUEST.value(), "Erro de validação!", LocalDateTime.now());
//        ex.getBindingResult().getFieldErrors().forEach(f -> errorResponse.addFieldError(f.getField(), f.getDefaultMessage()));
//        return ResponseEntity.badRequest().body(errorResponse);
        return ResponseEntity.badRequest().build();
    }

}
