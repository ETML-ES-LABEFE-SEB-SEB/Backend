package ch.etmles.Backend.Member.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MemberUnauthorizedAdvice {

    @ResponseBody
    @ExceptionHandler(MemberUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String memberUnauthorizedHandler(MemberUnauthorizedException ex) { return ex.getMessage(); }
}
