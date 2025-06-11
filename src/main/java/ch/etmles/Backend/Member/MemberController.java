package ch.etmles.Backend.Member;

import ch.etmles.Backend.Bid.*;
import ch.etmles.Backend.Bid.DTO.AddBidDTO;
import ch.etmles.Backend.Bid.DTO.BidDTO;
import ch.etmles.Backend.Bid.Exceptions.BidNotValidException;
import ch.etmles.Backend.Lot.DTO.FollowsLotsDTO;
import ch.etmles.Backend.Member.DTO.MemberAddDTO;
import ch.etmles.Backend.Member.Exceptions.MemberAlreadyExistsException;
import ch.etmles.Backend.ResponseAPI.ListPageApiResponse;
import ch.etmles.Backend.Lot.*;
import ch.etmles.Backend.Lot.DTO.LotDTO;
import ch.etmles.Backend.Lot.Exceptions.LotIsOwnByCurrentMemberException;
import ch.etmles.Backend.Member.DTO.MemberDTO;
import ch.etmles.Backend.Member.Exceptions.MemberInsufficientFundsException;
import ch.etmles.Backend.Member.Exceptions.MemberUnauthorizedException;
import ch.etmles.Backend.ResponseAPI.SingleApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ch.etmles.Backend.apiVersion.API_VERSION;

@RestController
@RequestMapping("/" + API_VERSION + "/me")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private final BidService bidService;
    private final LotService lotService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public MemberController(MemberRepository memberRepository, MemberService memberService, LotRepository lotRepository, BidRepository bidRepository, BidService bidService, LotService lotService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;
        this.bidService = bidService;
        this.lotService = lotService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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

    @GetMapping("follows")
    SingleApiResponse<FollowsLotsDTO> getFollowsLots() {

        Member currentUser = memberService.getCurrentMember();

        FollowsLotsDTO followsLots = lotService.getFollowLots(currentUser);

        return new SingleApiResponse<>(followsLots);
    }

    /* curl sample :
    curl -i localhost:8080/me/bids
    */
    @GetMapping("bids")
    ListPageApiResponse<BidDTO> getMemberBids(@RequestParam(defaultValue = "1") int page) {
        if(page < 1) page = 1;

        Member currentUser = memberService.getCurrentMember();
        if(currentUser == null)
            throw new MemberUnauthorizedException();

        Page<Bid> currentUserBids = bidRepository.findByOwner(
            currentUser,
            PageRequest.of(page-1, 12, Sort.by(Sort.Direction.DESC, "bidDate"))
        );
        return new ListPageApiResponse<>(currentUserBids.map(BidDTO::toDto));
    }

    /* curl sample :
    curl -i localhost:8080/me/lots
    */
    @GetMapping("lots")
    ListPageApiResponse<LotDTO> getMemberLots(@RequestParam(defaultValue = "1") int page) {
        if(page < 1) page = 1;

        Member currentUser = memberService.getCurrentMember();
        Page<Lot> currentUserLots = lotRepository.findByOwner(
            currentUser,
            PageRequest.of(page-1, 12, Sort.by(Sort.Direction.DESC, "endDate"))
        );

        return new ListPageApiResponse<>(currentUserLots.map(LotDTO::toDto));
    }

    /* curl sample :
    curl -i -X POST localhost:8080/bids/lots/UUID ^
        -H "Content-type:application/json" ^
        -d "{\"bidValue\": \"29.95\", \"bidDate\": \"12.05.2025\" }"
    */
    @PostMapping("bids")
    ResponseEntity<Bid> newBidForLot(@RequestBody AddBidDTO bid) {

        Lot lotToBidOn = lotService.getOpenLotById(UUID.fromString(bid.getLotId()));

        // Reject if currentUser owns the lot
        if(memberService.lotIsOwnByCurrentMember(lotToBidOn))
            throw new LotIsOwnByCurrentMemberException(lotToBidOn.getId());

        Bid newBid = new Bid();
        newBid.setBidUpLot(lotToBidOn);
        newBid.setOwner(memberService.getCurrentMember());
        newBid.setBidValue(bid.getAmount());
        newBid.setBidDate(LocalDateTime.now());

        if(!memberService.memberHasAvailableWallet(bid.getAmount()))
            throw new MemberInsufficientFundsException(memberService.getCurrentMember().getId());

        // Check validity of the bid (should be higher than the current best)
        if(bidService.checkBidValidity(newBid)) {
            bidRepository.save(newBid);
            lotToBidOn.setCurrentPrice(bid.getAmount());
            lotRepository.save(lotToBidOn);

            // Add bid value to the reserved wallet of the member
            memberService.moveToReservedWallet(newBid.getBidValue());
            
            return new ResponseEntity<>(newBid, HttpStatus.CREATED);
        } else {
            throw new BidNotValidException(lotToBidOn.getId());
        }
    }
}
