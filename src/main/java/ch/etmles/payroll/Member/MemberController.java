package ch.etmles.payroll.Member;

import ch.etmles.payroll.Bid.Bid;
import ch.etmles.payroll.Bid.BidDTO;
import ch.etmles.payroll.Lot.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository repository;

    public MemberController(MemberRepository repository) {
        this.repository = repository;
    }

    /* curl sample :
    curl -i localhost:8080/members
    */
    @GetMapping("")
    List<MemberDTO> getMembers() {
        List<Member> members = repository.findAll();
        List<MemberDTO> memberDTOs = new ArrayList<>();
        for (Member member : members) {
            memberDTOs.add(MemberDTO.toDTO(member));
        }
        return memberDTOs;
    }

    @GetMapping("{id}/bids")
    List<BidDTO> getMemberBids(@PathVariable UUID id) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
        List<BidDTO> bidDTOs = new ArrayList<BidDTO>();
        for (Bid bid : member.getBids()) {
            bidDTOs.add(BidDTO.toDto(bid));
        };
        return bidDTOs;
    }
}
