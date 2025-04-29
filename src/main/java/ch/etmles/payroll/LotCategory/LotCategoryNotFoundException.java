package ch.etmles.payroll.LotCategory;

public class LotCategoryNotFoundException extends RuntimeException {
    public LotCategoryNotFoundException(long id) {
        super("Could not find lot with id " + id);
    }
}
