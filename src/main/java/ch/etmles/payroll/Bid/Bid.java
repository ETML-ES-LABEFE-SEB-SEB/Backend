package ch.etmles.payroll.Bid;

import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Bid {
    private @Id
    @GeneratedValue UUID id;
    private BigDecimal bidValue;
    private LocalDateTime bidDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lot_id")
    private Lot bidUpLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member owner;

    public Bid() {}

    public Bid(BigDecimal bidValue, LocalDateTime bidDate, Lot bidUpLot, Member owner) {
        this.setBidValue(bidValue);
        this.setBidDate(bidDate);
        this.setBidUpLot(bidUpLot);
        this.setOwner(owner);
    }

    public Lot getBidUpLot() {
        return bidUpLot;
    }

    public void setBidUpLot(Lot bidUpLot) {
        this.bidUpLot = bidUpLot;
    }

    public LocalDateTime getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDateTime bidDate) {
        this.bidDate = bidDate;
    }

    public BigDecimal getBidValue() {
        return bidValue;
    }

    public void setBidValue(BigDecimal bidValue) {
        this.bidValue = bidValue;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member member) {
        this.owner = member;
    }
}
