package ch.etmles.Backend.Lot.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class LotNotOpenedAdvice {

    @ResponseBody
    @ExceptionHandler(LotNotOpenedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String lotNotFoundHandler(LotNotOpenedException ex) { return ex.getMessage(); }
}
