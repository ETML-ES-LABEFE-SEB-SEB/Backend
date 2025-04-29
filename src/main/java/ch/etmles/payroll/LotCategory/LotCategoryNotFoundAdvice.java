package ch.etmles.payroll.LotCategory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LotCategoryNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(LotCategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String lotCategoryNotFoundHandler(LotCategoryNotFoundException ex) { return ex.getMessage(); }
}
