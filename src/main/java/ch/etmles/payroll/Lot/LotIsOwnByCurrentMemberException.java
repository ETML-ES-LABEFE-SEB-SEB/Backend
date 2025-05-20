package ch.etmles.payroll.Lot;

import java.util.UUID;

public class LotIsOwnByCurrentMemberException extends RuntimeException {
    public LotIsOwnByCurrentMemberException(UUID id) {
        super("Can't bid on a lot you own : " + id);
    }
}
