package ch.etmles.Backend.Tag.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class TagNtoFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String tagNotFoundHandler(TagNotFoundException ex) { return ex.getMessage(); }
}
