package ch.etmles.Backend.Lot;

import ch.etmles.Backend.Bid.Bid;
import ch.etmles.Backend.Bid.BidService;
import ch.etmles.Backend.Bid.DTO.BidDTO;
import ch.etmles.Backend.Bid.BidRepository;
import ch.etmles.Backend.Lot.Exceptions.LotNotFoundException;
import ch.etmles.Backend.Lot.Exceptions.LotNotOpenedException;
import ch.etmles.Backend.LotCategory.CategoryRepository;
import ch.etmles.Backend.Member.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LotService {

    private final BidRepository bidRepository;
    private final LotRepository lotRepository;
    private final CategoryRepository categoryRepository;
    private final BidService bidService;

    public LotService(BidRepository bidRepository, LotRepository lotRepository, CategoryRepository categoryRepository, BidService bidService) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
        this.categoryRepository = categoryRepository;
        this.bidService = bidService;
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
            bidDTOs.add(BidDTO.toDto(bid));
        return bidDTOs;
    }

    public boolean closeLot(UUID lotId)
    {
        Member highestBidder = bidService.getHigherBidder(lotId);

        Optional<Lot> lot = lotRepository.findById(lotId);
        if(lot.isPresent()){
            // Remove the reserved amount from the bidder wallet
            highestBidder.setReservedWallet(highestBidder.getReservedWallet().subtract(lot.get().getCurrentPrice()));
        }

        return false;
    }
}
