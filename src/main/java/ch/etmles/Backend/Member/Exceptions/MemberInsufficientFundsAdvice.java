package ch.etmles.Backend.Member.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberInsufficientFundsAdvice {

    @ResponseBody
    @ExceptionHandler(MemberInsufficientFundsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String memberInsufficientFundsHandler(MemberInsufficientFundsException ex) { return ex.getMessage(); }
}
