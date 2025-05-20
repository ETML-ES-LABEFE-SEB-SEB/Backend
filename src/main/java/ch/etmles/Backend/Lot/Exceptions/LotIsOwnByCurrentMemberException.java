package ch.etmles.Backend.Lot.Exceptions;

import java.util.UUID;

public class LotIsOwnByCurrentMemberException extends RuntimeException {
    public LotIsOwnByCurrentMemberException(UUID id) {
        super("Can't bid on a lot you own : " + id);
    }
}
