package ch.etmles.payroll.Member;

import ch.etmles.payroll.Bid.Bid;
import ch.etmles.payroll.Lot.Lot;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Member {

    private @Id
    @GeneratedValue UUID id;

    private String username;
    private String profilePicture;
    private BigDecimal availableWallet;

    private BigDecimal reservedWallet;

    @OneToMany(mappedBy = "owner")
    private List<Bid> bids = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Lot> lots = new ArrayList<>();

    public Member() {}

    public Member(String username, String profilePicture, BigDecimal availableWallet, BigDecimal reservedWallet, List<Bid> bids) {
        this.setUsername(username);
        this.setProfilePicture(profilePicture);
        this.setAvailableWallet(availableWallet);
        this.setReservedWallet(reservedWallet);
        this.setBids(bids);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public BigDecimal getAvailableWallet() {
        return availableWallet;
    }

    public void setAvailableWallet(BigDecimal availableWallet) {
        this.availableWallet = availableWallet;
    }

    public BigDecimal getReservedWallet() {
        return reservedWallet;
    }

    public void setReservedWallet(BigDecimal reservedWallet) {
        this.reservedWallet = reservedWallet;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
        bid.setOwner(this);
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }
}
