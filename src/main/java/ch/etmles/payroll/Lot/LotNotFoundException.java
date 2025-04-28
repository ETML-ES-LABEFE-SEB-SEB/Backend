package ch.etmles.payroll.Lot;

public class LotNotFoundException extends RuntimeException {
    public LotNotFoundException(long id) { super("Could not find lot with id " + id); }
}
