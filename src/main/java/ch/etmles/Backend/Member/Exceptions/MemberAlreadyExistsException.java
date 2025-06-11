package ch.etmles.Backend.Member.Exceptions;

public class MemberAlreadyExistsException extends RuntimeException {
    public MemberAlreadyExistsException(String username) {
        super("User with this username already exists");
    }
}
