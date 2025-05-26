package ch.etmles.Backend.Bid;

import ch.etmles.Backend.Lot.*;
import ch.etmles.Backend.Member.Member;
import ch.etmles.Backend.Member.MemberRepository;
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

    public boolean checkBidValidity(Bid bid)
    {
        List<Bid> lotBids = bidRepository.findByBidUpLot(bid.getBidUpLot());
        lotBids.sort(Comparator.comparing(Bid::getBidValue));

        if(!lotBids.isEmpty()){
            Bid highest = lotBids.getLast();

            // newBid is higher than the current one, VALID !
            if(highest.getBidValue().compareTo(bid.getBidValue()) < 0) {

                // Update the lot
                bid.getBidUpLot().setCurrentPrice(bid.getBidValue());
                lotRepository.save(bid.getBidUpLot());
                return true;
            }
            else
                return false;
        }
        return true;
    }

    @Transactional
    public Member getBidderFromId(UUID memberId)
    {
        Optional<Member> bidder = memberRepository.findById(memberId);
        return bidder.orElse(null);
    }

    public Member getHigherBidder(UUID lotId)
    {
        // Lot exists ?
        Optional<Lot> lot = lotRepository.findById(lotId);
        if(lot.isEmpty())
            return null;

        // Nothing to do, the lot is already closed or paused
        if(lot.get().getStatus() != LotStatus.ACTIVATED)
            return null;

        Optional<Bid> highest = lot.get().getBids().stream().max(Comparator.comparing(Bid::getBidValue));

        // No bidder for this lot
        if(highest.isEmpty())
            return null;

        return highest.get().getOwner();
    }
}
