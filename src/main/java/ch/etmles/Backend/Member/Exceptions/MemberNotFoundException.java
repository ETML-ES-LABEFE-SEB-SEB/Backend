package ch.etmles.Backend.Member.Exceptions;

import java.util.UUID;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String username) {
        super("Could not find member with username " + username);
    }
}
