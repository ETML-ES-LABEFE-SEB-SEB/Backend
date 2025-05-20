package ch.etmles.payroll.Member.DTO;

import ch.etmles.payroll.Member.Member;

import java.math.BigDecimal;
import java.util.UUID;

public class MemberDTO {

    private UUID id;
    private String username;
    private String profilePicture;
    private BigDecimal availableWallet;
    private BigDecimal reservedWallet;

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

    public MemberDTO() {}
    public MemberDTO(UUID id, String username, String profilePicture, BigDecimal availableWallet, BigDecimal reservedWallet) {
        this.setId(id);
        this.setUsername(username);
        this.setProfilePicture(profilePicture);
        this.setAvailableWallet(availableWallet);
        this.setReservedWallet(reservedWallet);
    }

    public static MemberDTO toDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setUsername(member.getUsername());
        memberDTO.setProfilePicture(member.getProfilePicture());
        memberDTO.setAvailableWallet(member.getAvailableWallet());
        memberDTO.setReservedWallet(member.getReservedWallet());
        return memberDTO;
    }
}
