package ch.etmles.payroll.Bid;

import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotNotFoundException;
import ch.etmles.payroll.Lot.LotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bids")
public class BidController {

    private final BidRepository repository;
    private final BidService bidService;
    private final LotService lotService;

    public BidController(BidRepository bidRepository, BidService bidService, LotService lotService) {
        this.repository = bidRepository;
        this.bidService = bidService;
        this.lotService = lotService;
    }

    /* curl sample :
    curl -i localhost:8080/bids
    */
    @GetMapping("{id}")
    BidDTO one(@PathVariable UUID id) {
        Bid bid = repository.findById(id)
                .orElseThrow(() -> new LotNotFoundException(id));
        return BidDTO.toDto(bid);
    }

//    /* curl sample :
//    curl -i localhost:8080/lots/UUID
//     */
//    @GetMapping("lots/{lotId}")
//    List<BidDTO> getBidsForLot(@PathVariable UUID lotId) {
//        return service.getBidsForLot(lotId);
//    }

    @GetMapping("lots/{lotId}/bids-with-members")
    List<BidDTO> getBidsWithMembers(@PathVariable UUID lotId) {
        return lotService.getBidsForLot(lotId);
    }

    /* curl sample :
    curl -i -X POST localhost:8080/bids/lots/UUID ^
        -H "Content-type:application/json" ^
        -d "{\"bidValue\": \"29.95\", \"bidDate\": \"12.05.2025\" }"
    */
    @PostMapping("lots/{lotId}")
    ResponseEntity<Bid> newBidForLot(@PathVariable UUID lotId,@RequestBody BidDTO bid) {
        Lot lotToBidOn = lotService.getOpenLotById(lotId);
        Bid newBid = new Bid();
        newBid.setBidUpLot(lotToBidOn);
        newBid.setBidDate(bid.getBidDate());
        newBid.setBidValue(bid.getBidValue());
        if(bidService.checkBidValidity(newBid))
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(newBid));
        throw new BidTooLowException(lotToBidOn.getId());
    }

    @PutMapping("{id}")
    Bid replaceBid(@PathVariable UUID id, @RequestBody Bid newBid) {
        return repository.findById(id)
                .map(bid -> {
                    bid.setBidUpLot(newBid.getBidUpLot());
                    bid.setBidDate(newBid.getBidDate());
                    bid.setBidValue(newBid.getBidValue());
                    return repository.save(bid);
                })
                .orElseGet(() -> {
                    newBid.setId(id);
                    return repository.save(newBid);
                });
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> deleteBid(@PathVariable UUID id) {
        if(!repository.existsById(id)) {
            throw new LotNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Bid deleted");
    }
}
