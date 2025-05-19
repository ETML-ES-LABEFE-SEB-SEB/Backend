package ch.etmles.payroll.Bid;

import ch.etmles.payroll.Lot.*;
import ch.etmles.payroll.Member.Member;
import ch.etmles.payroll.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BidService {

    private final BidRepository bidRepository;
    private final LotRepository lotRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BidService(BidRepository bidRepository, LotRepository lotRepository, MemberRepository memberRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public boolean checkBidValidity(Bid bid)
    {
        List<Bid> lotBids = bidRepository.findByBidUpLot(bid.getBidUpLot());
        lotBids.sort((b1, b2) -> b1.getBidValue().compareTo(b2.getBidValue()));
        Bid highest = lotBids.getLast();

        // newBid is higher than the current one, VALID !
        if(highest.getBidValue().compareTo(bid.getBidValue()) < 0)
            return true;

        return false;
    }

    @Transactional
    public Member getBidderFromId(UUID memberId)
    {
        Optional<Member> bidder = memberRepository.findById(memberId);
        return bidder.orElse(null);
    }
}
