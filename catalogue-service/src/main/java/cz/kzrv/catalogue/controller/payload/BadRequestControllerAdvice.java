package cz.kzrv.catalogue.controller.payload;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class BadRequestControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException ex, Locale locale) {

        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST,this.messageSource.getMessage("errors.400.title",new Object[0],"errors.400.title",locale));
        problemDetail.setProperty("errors",
                ex.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage).toList());
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
