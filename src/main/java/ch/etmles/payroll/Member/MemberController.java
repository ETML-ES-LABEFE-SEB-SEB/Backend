package ch.etmles.payroll.Member;

import ch.etmles.payroll.Bid.Bid;
import ch.etmles.payroll.Bid.BidDTO;
import ch.etmles.payroll.Bid.BidRepository;
import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotDTO;
import ch.etmles.payroll.Lot.LotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class MemberController {

    private final MemberRepository repository;
    private final MemberService memberService;
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;

    public MemberController(MemberRepository repository, MemberService memberService, LotRepository lotRepository, BidRepository bidRepository) {
        this.repository = repository;
        this.memberService = memberService;
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
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

    @GetMapping("me")
    MemberDTO getMember() {
        Member currentUser = memberService.getCurrentMember();
        if(currentUser != null)
            return MemberDTO.toDTO(currentUser);
        return null;
    }

    @GetMapping("me/bids")
    Page<BidDTO> getMemberBids(@RequestParam(defaultValue = "1") int page) {
        if(page < 1) page = 1;

        Member currentUser = memberService.getCurrentMember();
        Page<Bid> currentUserBids = bidRepository.findByOwner(
            currentUser,
            PageRequest.of(page-1, 12, Sort.by(Sort.Direction.DESC, "bidDate"))
        );
        return currentUserBids.map(BidDTO::toDto);
    }

    @GetMapping("me/lots")
    Page<LotDTO> getMemberLots(@RequestParam(defaultValue = "1") int page) {
        if(page < 1) page = 1;

        Member currentUser = memberService.getCurrentMember();
        Page<Lot> currentUserLots = lotRepository.findByOwner(
            currentUser,
            PageRequest.of(page-1, 12, Sort.by(Sort.Direction.DESC, "endDate"))
        );

        return currentUserLots.map(LotDTO::toDto);
    }
}
