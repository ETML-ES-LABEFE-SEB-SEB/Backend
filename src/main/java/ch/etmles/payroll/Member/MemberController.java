package ch.etmles.payroll.Member;

import ch.etmles.payroll.Bid.Bid;
import ch.etmles.payroll.Bid.BidDTO;
import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository repository;
    private final MemberService memberService;

    public MemberController(MemberRepository repository, MemberService memberService) {
        this.repository = repository;
        this.memberService = memberService;
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

    @GetMapping("me/bids")
    List<BidDTO> getMemberBids() {
        Member currentUser = memberService.getCurrentMember();
        List<BidDTO> bidDTOs = new ArrayList<BidDTO>();
        for (Bid bid : currentUser.getBids()) {
            bidDTOs.add(BidDTO.toDto(bid));
        };
        return bidDTOs;
    }

    @GetMapping("me/lots")
    List<LotDTO> getMemberLots() {
        Member currentUser = memberService.getCurrentMember();
        List<LotDTO> lotDTOs = new ArrayList<LotDTO>();
        for (Lot lot : currentUser.getLots()) {
            lotDTOs.add(LotDTO.toDto(lot));
        };
        return lotDTOs;
    }
}
