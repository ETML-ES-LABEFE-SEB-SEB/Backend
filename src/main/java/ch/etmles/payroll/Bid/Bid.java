package ch.etmles.payroll.Bid;

import ch.etmles.payroll.Lot.Lot;
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

    @OneToOne
    @JoinColumn(name="lot_Id")
    private Lot bidUpLot;

    public Bid() {}

    public Bid(BigDecimal bidValue, LocalDateTime bidDate, Lot bidUpLot) {
        this.setBidValue(bidValue);
        this.setBidDate(bidDate);
        this.setBidUpLot(bidUpLot);
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
}
