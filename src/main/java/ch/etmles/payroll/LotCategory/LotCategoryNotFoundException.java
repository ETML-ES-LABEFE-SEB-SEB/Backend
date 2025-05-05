package ch.etmles.payroll.LotCategory;

import java.util.UUID;

public class LotCategoryNotFoundException extends RuntimeException {
    public LotCategoryNotFoundException(UUID id) {
        super("Could not find lot with id " + id);
    }
}
