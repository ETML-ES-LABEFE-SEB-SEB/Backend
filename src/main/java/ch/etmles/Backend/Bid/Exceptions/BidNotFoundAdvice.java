package ch.etmles.Backend.Bid.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class BidNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(BidNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bidNotFoundHandler(BidNotFoundException ex) { return ex.getMessage(); }
}
