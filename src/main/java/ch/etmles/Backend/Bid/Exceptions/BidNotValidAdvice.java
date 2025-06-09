package ch.etmles.Backend.Bid.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class BidNotValidAdvice {

    @ResponseBody
    @ExceptionHandler(BidNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bidTooLowException(BidNotValidException ex) { return ex.getMessage(); }
}
