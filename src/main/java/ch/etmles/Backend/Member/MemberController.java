package ch.etmles.Backend.Member;

import ch.etmles.Backend.Bid.*;
import ch.etmles.Backend.Bid.DTO.AddBidDTO;
import ch.etmles.Backend.Bid.DTO.BidDTO;
import ch.etmles.Backend.Bid.Exceptions.BidTooLowException;
import ch.etmles.Backend.ListApiResponse;
import ch.etmles.Backend.Lot.*;
import ch.etmles.Backend.Lot.DTO.LotDTO;
import ch.etmles.Backend.Lot.Exceptions.LotIsOwnByCurrentMemberException;
import ch.etmles.Backend.Member.DTO.MemberDTO;
import ch.etmles.Backend.Member.Exceptions.MemberUnauthorizedAdvice;
import ch.etmles.Backend.Member.Exceptions.MemberUnauthorizedException;
import ch.etmles.Backend.SingleApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/me/")
public class MemberController {

    private final MemberRepository repository;
    private final MemberService memberService;
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private final BidService bidService;
    private final LotService lotService;

    public MemberController(MemberRepository repository, MemberService memberService, LotRepository lotRepository, BidRepository bidRepository, BidService bidService, LotService lotService) {
        this.repository = repository;
        this.memberService = memberService;
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
        this.bidService = bidService;
        this.lotService = lotService;
    }

    /* curl sample :
    curl -i localhost:8080/me
    */
    @GetMapping("")
    SingleApiResponse<MemberDTO> getMember() {
        Member currentUser = memberService.getCurrentMember();
        if(currentUser != null)
            return new SingleApiResponse<>(MemberDTO.toDTO(currentUser));
        throw new MemberUnauthorizedException();
    }

    /* curl sample :
    curl -i localhost:8080/me/bids
    */
    @GetMapping("bids")
    ListApiResponse<BidDTO> getMemberBids(@RequestParam(defaultValue = "1") int page) {
        if(page < 1) page = 1;

        Member currentUser = memberService.getCurrentMember();
        if(currentUser == null)
            throw new MemberUnauthorizedException();

        Page<Bid> currentUserBids = bidRepository.findByOwner(
            currentUser,
            PageRequest.of(page-1, 12, Sort.by(Sort.Direction.DESC, "bidDate"))
        );
        return new ListApiResponse<>(currentUserBids.map(BidDTO::toDto));
    }

    /* curl sample :
    curl -i localhost:8080/me/lots
    */
    @GetMapping("lots")
    ListApiResponse<LotDTO> getMemberLots(@RequestParam(defaultValue = "1") int page) {
        if(page < 1) page = 1;

        Member currentUser = memberService.getCurrentMember();
        Page<Lot> currentUserLots = lotRepository.findByOwner(
            currentUser,
            PageRequest.of(page-1, 12, Sort.by(Sort.Direction.DESC, "endDate"))
        );

        return new ListApiResponse<>(currentUserLots.map(LotDTO::toDto));
    }

    /* curl sample :
    curl -i -X POST localhost:8080/bids/lots/UUID ^
        -H "Content-type:application/json" ^
        -d "{\"bidValue\": \"29.95\", \"bidDate\": \"12.05.2025\" }"
    */
    @PostMapping("bids")
    ResponseEntity<Bid> newBidForLot(AddBidDTO bid) {

        Lot lotToBidOn = lotService.getOpenLotById(bid.getLotId());

        // Reject if currentUser owns the lot
        if(memberService.lotIsOwnByCurrentMember(lotToBidOn))
            throw new LotIsOwnByCurrentMemberException(lotToBidOn.getId());

        Bid newBid = new Bid();
        newBid.setBidUpLot(lotToBidOn);
        newBid.setOwner(memberService.getCurrentMember());
        newBid.setBidValue(bid.getAmount());
        newBid.setBidDate(LocalDateTime.now());

        // Check validity of the bid (should be higher than the current best)
        if(bidService.checkBidValidity(newBid)) {
            bidRepository.save(newBid);
            return new ResponseEntity<>(newBid, HttpStatus.CREATED);
        } else {
            throw new BidTooLowException(lotToBidOn.getId());
        }
    }
}
