package ch.etmles.Backend.Bid.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class BidTooLowAdvice {

    @ResponseBody
    @ExceptionHandler(BidTooLowException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String bidTooLowException(BidTooLowException ex) { return ex.getMessage(); }
}
