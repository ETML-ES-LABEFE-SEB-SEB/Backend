package ch.etmles.Backend.Member.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class MemberNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String memberNotFoundHandler(MemberNotFoundException ex) { return ex.getMessage(); }
}
