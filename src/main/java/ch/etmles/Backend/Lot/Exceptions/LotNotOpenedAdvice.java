package ch.etmles.Backend.Lot.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LotNotOpenedAdvice {

    @ResponseBody
    @ExceptionHandler(LotNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String lotNotFoundHandler(LotNotOpenedException ex) { return ex.getMessage(); }
}
