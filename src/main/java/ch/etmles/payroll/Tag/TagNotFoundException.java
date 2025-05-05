package ch.etmles.payroll.Tag;

public class TagNotFoundException extends RuntimeException {
    public TagNotFoundException(long id) {
        super("Could not find tag with id " + id);
    }
}
