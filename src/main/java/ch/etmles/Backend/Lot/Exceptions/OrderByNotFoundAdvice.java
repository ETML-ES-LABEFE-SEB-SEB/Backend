package ch.etmles.Backend.Lot.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class OrderByNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(OrderByNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String lotNotFoundHandler(OrderByNotFoundException ex) { return ex.getMessage(); }
}
