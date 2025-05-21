package ch.etmles.Backend.LotCategory.Exceptions;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(UUID id) {
        super("Could not find category with id " + id);
    }
}
