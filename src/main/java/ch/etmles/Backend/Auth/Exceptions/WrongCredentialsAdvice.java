package ch.etmles.Backend.Auth.Exceptions;

import ch.etmles.Backend.Member.Exceptions.MemberAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WrongCredentialsAdvice {

    @ResponseBody
    @ExceptionHandler(WrongCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String wrongCredentialsHandler(WrongCredentialsException ex) { return ex.getMessage(); }
}
