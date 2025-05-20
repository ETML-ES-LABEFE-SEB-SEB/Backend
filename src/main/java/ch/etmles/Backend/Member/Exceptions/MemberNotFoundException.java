package ch.etmles.Backend.Member.Exceptions;

import java.util.UUID;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(UUID id) {
        super("Could not find member with id " + id);
    }
}
