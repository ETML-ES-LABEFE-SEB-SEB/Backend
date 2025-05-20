package ch.etmles.payroll.Member;

import java.util.UUID;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(UUID id) {
        super("Could not find member with id " + id);
    }
}
