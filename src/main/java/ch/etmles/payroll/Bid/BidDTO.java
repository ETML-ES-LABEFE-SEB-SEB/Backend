package ch.etmles.payroll.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BidDTO {
    private BigDecimal bidValue;
    private LocalDateTime bidDate;

    public BigDecimal getBidValue() {
        return bidValue;
    }

    public void setBidValue(BigDecimal bidValue) {
        this.bidValue = bidValue;
    }

    public LocalDateTime getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDateTime bidDate) {
        this.bidDate = bidDate;
    }

    public BidDTO toDto(Bid bid) {
        BidDTO dto = new BidDTO();
        dto.setBidValue(bid.getBidValue());
        dto.setBidDate(bid.getBidDate());
        return dto;
    }
}
