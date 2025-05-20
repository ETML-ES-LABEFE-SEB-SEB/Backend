package ch.etmles.payroll.LotCategory.Exceptions;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(UUID id) {
        super("Could not find lot with id " + id);
    }
}
