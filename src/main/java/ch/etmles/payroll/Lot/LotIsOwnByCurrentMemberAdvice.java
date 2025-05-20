package ch.etmles.payroll.Lot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LotIsOwnByCurrentMemberAdvice {
    @ResponseBody
    @ExceptionHandler(LotNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String LotIsOwnByCurrentMemberHandler(LotIsOwnByCurrentMemberException ex) { return ex.getMessage(); }
}
