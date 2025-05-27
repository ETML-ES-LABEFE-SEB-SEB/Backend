package ch.etmles.Backend.Member.DTO;

import ch.etmles.Backend.Member.Member;

import java.util.UUID;

public class SummaryMemberDTO {

    private UUID id;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static SummaryMemberDTO toDTO(Member member) {
        SummaryMemberDTO dto = new SummaryMemberDTO();
        dto.setUsername(member.getUsername());
        dto.setId(member.getId());
        return dto;
    }
}
