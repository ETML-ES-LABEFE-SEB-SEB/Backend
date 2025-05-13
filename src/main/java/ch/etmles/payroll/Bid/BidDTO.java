package ch.etmles.payroll.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BidDTO {

    private String username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static BidDTO toDto(Bid bid) {
        BidDTO dto = new BidDTO();
        dto.setUsername(bid.getMember().getUsername());
        dto.setBidValue(bid.getBidValue());
        dto.setBidDate(bid.getBidDate());
        return dto;
    }
}
