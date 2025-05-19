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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="lot_Id")
    private Lot bidUpLot;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    public Bid() {}

    public Bid(BigDecimal bidValue, LocalDateTime bidDate, Lot bidUpLot, Member member) {
        this.setBidValue(bidValue);
        this.setBidDate(bidDate);
        this.setBidUpLot(bidUpLot);
        this.setMember(member);
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
