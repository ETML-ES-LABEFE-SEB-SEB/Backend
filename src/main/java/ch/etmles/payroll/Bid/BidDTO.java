package ch.etmles.payroll.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BidDTO {

    private UUID id;
    private String username;
    private String profilePicture;
    private BigDecimal bidValue;
    private LocalDateTime bidDate;

    private UUID lotId;

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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UUID getLotId() {
        return lotId;
    }

    public void setLotId(UUID lotId) {
        this.lotId = lotId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static BidDTO toDto(Bid bid) {
        BidDTO dto = new BidDTO();
        dto.setId(bid.getId());
        dto.setUsername(bid.getOwner().getUsername());
        dto.setProfilePicture(bid.getOwner().getProfilePicture());
        dto.setBidValue(bid.getBidValue());
        dto.setBidDate(bid.getBidDate());
        dto.setLotId(bid.getBidUpLot().getId());
        return dto;
    }
}
