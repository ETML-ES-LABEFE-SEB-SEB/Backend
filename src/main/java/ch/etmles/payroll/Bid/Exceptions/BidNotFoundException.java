package ch.etmles.payroll.Bid.Exceptions;

import java.util.UUID;

public class BidNotFoundException extends RuntimeException {
    public BidNotFoundException(UUID id) {

        super("Could not find bid with id " + id);
    }
}
