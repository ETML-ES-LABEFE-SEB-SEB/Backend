package ch.etmles.Backend.Bid;

import ch.etmles.Backend.Bid.DTO.BidDTO;
import ch.etmles.Backend.Bid.Exceptions.BidNotFoundException;
import ch.etmles.Backend.Lot.Exceptions.LotNotFoundException;
import ch.etmles.Backend.Lot.LotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static ch.etmles.Backend.apiVersion.API_VERSION;

@RestController
@RequestMapping("/" + API_VERSION + "/bids")
public class BidController {

    private final BidRepository repository;
    private final LotService lotService;

    public BidController(BidRepository bidRepository, LotService lotService) {
        this.repository = bidRepository;
        this.lotService = lotService;
    }

    /* curl sample :
    curl -i localhost:8080/bids
    */
    @GetMapping("{id}")
    BidDTO one(@PathVariable UUID id) {
        Bid bid = repository.findById(id)
                .orElseThrow(() -> new BidNotFoundException(id));
        return BidDTO.toDto(bid);
    }

    @GetMapping("lots/{lotId}/bids-with-members")
    List<BidDTO> getBidsWithMembers(@PathVariable UUID lotId) {
        return lotService.getBidsForLot(lotId);
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
