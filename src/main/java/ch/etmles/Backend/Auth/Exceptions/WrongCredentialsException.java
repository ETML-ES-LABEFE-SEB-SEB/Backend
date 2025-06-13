package ch.etmles.Backend.Auth.Exceptions;

public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException() {
        super("Username or password is incorrect.");
    }
}
