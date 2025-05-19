package ch.etmles.payroll.Lot;

import ch.etmles.payroll.Bid.Bid;
import ch.etmles.payroll.Bid.BidDTO;
import ch.etmles.payroll.Bid.BidRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LotService {

    private final BidRepository bidRepository;
    private final LotRepository lotRepository;

    public LotService(BidRepository bidRepository, LotRepository lotRepository) {
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
            bidDTOs.add(BidDTO.toDto(bid));
        return bidDTOs;
    }
}
