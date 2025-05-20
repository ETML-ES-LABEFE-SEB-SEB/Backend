package ch.etmles.payroll.Lot;

import ch.etmles.payroll.Bid.Bid;
import ch.etmles.payroll.Bid.BidDTO;
import ch.etmles.payroll.Bid.BidRepository;
import ch.etmles.payroll.LotCategory.Category;
import ch.etmles.payroll.LotCategory.CategoryRepository;
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

    public LotService(BidRepository bidRepository, LotRepository lotRepository, CategoryRepository categoryRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
        this.categoryRepository = categoryRepository;
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
