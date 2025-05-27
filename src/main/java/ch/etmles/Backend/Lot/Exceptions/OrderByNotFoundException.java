package ch.etmles.Backend.Lot.Exceptions;

public class OrderByNotFoundException extends RuntimeException{
    public OrderByNotFoundException(String orderByValue){
        super("OrderBy value not found: " + orderByValue);
    }
}
