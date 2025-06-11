package ch.etmles.Backend.Member;

import ch.etmles.Backend.Bid.Bid;
import ch.etmles.Backend.Lot.Lot;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class Member implements UserDetails {

    private @Id
    @GeneratedValue UUID id;

    private String username;
    private String passwordHash;
    private MemberRole role;
    private String profilePicture;
    private BigDecimal availableWallet;

    private BigDecimal reservedWallet;

    @OneToMany(mappedBy = "owner")
    private List<Bid> bids = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Lot> lots = new ArrayList<>();

    public Member() {}

    public Member(String username, String passwordHash, String profilePicture, BigDecimal availableWallet, BigDecimal reservedWallet, List<Bid> bids) {
        this.setUsername(username);
        this.setPasswordHash(passwordHash);
        this.setRole(MemberRole.USER);
        this.setProfilePicture(profilePicture);
        this.setAvailableWallet(availableWallet);
        this.setReservedWallet(reservedWallet);
        this.setBids(bids);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
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
