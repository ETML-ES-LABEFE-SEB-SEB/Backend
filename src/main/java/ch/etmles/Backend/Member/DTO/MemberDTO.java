package ch.etmles.Backend.Member.DTO;

import ch.etmles.Backend.Member.Member;

import java.math.BigDecimal;
import java.util.UUID;

public class MemberDTO {

    private UUID id;
    private String username;
    private String role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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

    public MemberDTO() {}

    public static MemberDTO toDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setUsername(member.getUsername());
        memberDTO.setRole(member.getRole().toString());
        memberDTO.setProfilePicture(member.getProfilePicture());
        memberDTO.setAvailableWallet(member.getAvailableWallet());
        memberDTO.setReservedWallet(member.getReservedWallet());
        return memberDTO;
    }
}
