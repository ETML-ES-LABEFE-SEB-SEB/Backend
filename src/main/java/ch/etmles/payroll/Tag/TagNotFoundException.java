package ch.etmles.payroll.Tag;

import java.util.UUID;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(UUID id) {
        super("Could not find tag with id " + id);
    }
}
