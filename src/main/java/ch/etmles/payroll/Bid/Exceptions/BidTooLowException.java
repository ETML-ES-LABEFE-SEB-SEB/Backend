package ch.etmles.payroll.Bid.Exceptions;

import java.util.UUID;

public class BidTooLowException extends RuntimeException {
    public BidTooLowException(UUID lotId) {
      super("A higher bid is already set for the lot " + lotId);
    }
}
