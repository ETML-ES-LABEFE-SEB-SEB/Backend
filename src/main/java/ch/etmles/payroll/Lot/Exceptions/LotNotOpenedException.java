package ch.etmles.payroll.Lot.Exceptions;

import java.util.UUID;

public class LotNotOpenedException extends RuntimeException {
    public LotNotOpenedException(UUID id) {
        super("Lot: " + id + " is not opened");
    }
}
