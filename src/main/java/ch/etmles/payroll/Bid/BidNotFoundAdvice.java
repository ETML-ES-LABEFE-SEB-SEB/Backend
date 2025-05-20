package ch.etmles.payroll.Bid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BidNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(BidNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bidNotFoundHandler(BidNotFoundException ex) { return ex.getMessage(); }
}
