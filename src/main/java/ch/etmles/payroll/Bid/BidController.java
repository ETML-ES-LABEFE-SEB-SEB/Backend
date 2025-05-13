package ch.etmles.payroll.Bid;

import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bids")
public class BidController {

    private final BidRepository repository;
    private final BidService service;

    public BidController(BidRepository bidRepository, BidService service) {
        this.repository = bidRepository;
        this.service = service;
    }

    /* curl sample :
    curl -i localhost:8080/bids
    */
    @GetMapping("{id}")
    Bid one(@PathVariable UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new LotNotFoundException(id));
    }

    @GetMapping("lots/{lotId}")
    List<Bid> lotsBids(@PathVariable UUID lotId) {
        return service.getBidsForLot(lotId);
    }

    /* curl sample :
    curl -i -X POST localhost:8080/bids/lots/UUID ^
        -H "Content-type:application/json" ^
        -d "{\"bidValue\": \"29.95\", \"bidDate\": \"12.05.2025\" }"
    */
    @PostMapping("lots/{lotId}")
    ResponseEntity<Bid> newLot(@PathVariable UUID lotId,@RequestBody Bid bid) {
        Lot lotToBidOn = service.getOpenLotById(lotId);
        bid.setBidUpLot(lotToBidOn);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(bid));
    }
}
