package ch.etmles.Backend.Lot.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class LotIsOwnByCurrentMemberAdvice {
    @ResponseBody
    @ExceptionHandler(LotNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String LotIsOwnByCurrentMemberHandler(LotIsOwnByCurrentMemberException ex) { return ex.getMessage(); }
}
