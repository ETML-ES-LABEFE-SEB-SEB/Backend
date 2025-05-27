package ch.etmles.Backend.Member.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class MemberUnauthorizedAdvice {

    @ResponseBody
    @ExceptionHandler(MemberUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String memberUnauthorizedHandler(MemberUnauthorizedException ex) { return ex.getMessage(); }
}
