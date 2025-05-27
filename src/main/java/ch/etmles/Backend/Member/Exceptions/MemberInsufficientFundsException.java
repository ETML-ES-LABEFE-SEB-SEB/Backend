package ch.etmles.Backend.Member.Exceptions;

import java.util.UUID;

public class MemberInsufficientFundsException extends RuntimeException {

    public MemberInsufficientFundsException(UUID id) {
        super("Member " + id + " has insufficient funds");
    }
}
