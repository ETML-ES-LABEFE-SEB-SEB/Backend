package ch.etmles.payroll.Bid;

import ch.etmles.payroll.Lot.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class BidService {

    private final BidRepository bidRepository;
    private final LotRepository lotRepository;

    @Autowired
    public BidService(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }

    public Lot getOpenLotById(UUID lotId)
    {
        Lot lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new LotNotFoundException(lotId));

        if(lot.getStatus() == LotStatus.ACTIVATED)
            return lot;

        throw new LotNotOpenedException(lotId);
    }

    public List<BidDTO> getBidsForLot(UUID lotId)
    {
        Lot lot = getOpenLotById(lotId);
        List<Bid> bids = bidRepository.findByBidUpLot(lot);
        List<BidDTO> bidDTOs = new ArrayList<BidDTO>();
        for(Bid bid : bids)
            bidDTOs.add(new BidDTO().toDto(bid));
        return bidDTOs;
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
}
