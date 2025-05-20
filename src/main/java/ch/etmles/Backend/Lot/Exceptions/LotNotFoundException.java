package ch.etmles.Backend.Lot.Exceptions;

import java.util.UUID;

public class LotNotFoundException extends RuntimeException {
    public LotNotFoundException(UUID id) {
        super("Could not find lot with id " + id);
    }
}
