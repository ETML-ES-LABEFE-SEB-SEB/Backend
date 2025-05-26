package ch.etmles.Backend.Lot;

import ch.etmles.Backend.Bid.Bid;
import ch.etmles.Backend.Bid.BidService;
import ch.etmles.Backend.Bid.DTO.BidDTO;
import ch.etmles.Backend.Bid.BidRepository;
import ch.etmles.Backend.Lot.Exceptions.LotNotFoundException;
import ch.etmles.Backend.Lot.Exceptions.LotNotOpenedException;
import ch.etmles.Backend.LotCategory.CategoryRepository;
import ch.etmles.Backend.Member.Member;
import ch.etmles.Backend.Member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final MemberRepository memberRepository;

    public LotService(BidRepository bidRepository, LotRepository lotRepository, CategoryRepository categoryRepository, BidService bidService, MemberRepository memberRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
        this.categoryRepository = categoryRepository;
        this.bidService = bidService;
        this.memberRepository = memberRepository;
    }

    public Lot getOpenLotById(UUID lotId)
    {
        Lot lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new LotNotFoundException(lotId));

        if(lot.getStatus() == LotStatus.ACTIVATED)
            return lot;

        throw new LotNotOpenedException(lotId);
    }

    @Transactional
    public List<BidDTO> getBidsForLot(UUID lotId)
    {
        Lot lot = getOpenLotById(lotId);
        List<Bid> bids = bidRepository.findByBidUpLot(lot);
        List<BidDTO> bidDTOs = new ArrayList<BidDTO>();
        for(Bid bid : bids)
            bidDTOs.add(BidDTO.toDto(bid));
        return bidDTOs;
    }

    @Transactional
    public List<Lot> getExpiredActivatedLots()
    {
        List<Lot> lots = lotRepository.getLotByStatusAndEndDateBefore(LotStatus.ACTIVATED, LocalDateTime.now());
        return lots;
    }

    @Transactional
    public boolean closeLot(UUID lotId)
    {
        Member highestBidder = bidService.getHighestBidder(lotId);

        Optional<Lot> lot = lotRepository.findById(lotId);
        if(lot.isPresent()){

            // Remove the reserved amount from the bidder wallet
            if(highestBidder != null) {
                highestBidder.setReservedWallet(highestBidder.getReservedWallet().subtract(lot.get().getCurrentPrice()));
                memberRepository.save(highestBidder);
            }

            // Set the lot to finished status
            lot.get().setStatus(LotStatus.FINISHED);
            lotRepository.save(lot.get());

            return true;
        }

        return false;
    }
}
