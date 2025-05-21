package ch.etmles.Backend.Member.Exceptions;


public class MemberUnauthorizedException extends RuntimeException {
    public MemberUnauthorizedException() {
        super("Not authorized");
    }
}
