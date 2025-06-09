package ch.etmles.Backend.Bid.Exceptions;

import java.util.UUID;

public class BidNotValidException extends RuntimeException {
    public BidNotValidException(UUID lotId) {
      super("The bid is not valid for the lot: " + lotId);
    }
}
