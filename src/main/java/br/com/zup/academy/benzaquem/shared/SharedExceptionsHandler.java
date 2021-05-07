package br.com.zup.academy.benzaquem.shared;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SharedExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        FieldError fe = ex.getBindingResult().getFieldError("documento");
        if (fe != null && fe.getCode().equals("CampoUnico"))
            return ResponseEntity.unprocessableEntity().build();
        return ResponseEntity.badRequest().build();
    }

}
