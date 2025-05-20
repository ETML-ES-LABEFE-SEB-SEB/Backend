package ch.etmles.payroll.Lot;

import java.util.UUID;

public class LotNotOpenedException extends RuntimeException {
    public LotNotOpenedException(UUID id) {
        super("Lot: " + id + " is not opened");
    }
}
