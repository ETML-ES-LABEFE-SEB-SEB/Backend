package ch.etmles.Backend.Bid.DTO;

import ch.etmles.Backend.Bid.Bid;

import java.math.BigDecimal;
import java.util.UUID;

public class AddBidDTO {

    private UUID lotId;
    private BigDecimal amount;

    public UUID getLotId() {
        return lotId;
    }

    public void setLotId(UUID lotId) {
        this.lotId = lotId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static AddBidDTO toDTO(Bid bid) {
        AddBidDTO dto = new AddBidDTO();
        dto.setLotId(bid.getBidUpLot().getId());
        dto.setAmount(bid.getBidValue());
        return dto;
    }
}
