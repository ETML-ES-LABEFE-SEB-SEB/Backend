package ch.etmles.Backend.Bid.Exceptions;

import java.util.UUID;

public class BidTooLowException extends RuntimeException {
    public BidTooLowException(UUID lotId) {
      super("A higher bid is already set for the lot " + lotId);
    }
}
