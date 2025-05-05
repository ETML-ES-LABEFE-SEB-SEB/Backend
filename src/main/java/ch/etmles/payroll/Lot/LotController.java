package ch.etmles.payroll.Lot;

import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class LotController {

    private final LotRepository repository;

    public LotController(LotRepository repository) {
        this.repository = repository;
    }

    /* curl sample :
    curl -i localhost:8080/lots
    */
    @GetMapping("/lots")
    public Page<Lot> getLots(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    /* curl sample :
    curl -i localhost:8080/lots/1
    */
    @GetMapping("/lots/{id}")
    Lot one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LotNotFoundException(id));
    }

    /* curl sample :
    curl -i -X POST localhost:8080/lots ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"description\": \"test description\", \"pictureUrl\": \"https://picsum.photos/id/237/600/400\", \"startPrice\": "12.95", \"startDate\": \"2025-04-25 12:30\", \"startDate\": \"2025-04-30 12:30\", \"category\": null, \"status\": \"ACTIVATED\" }"
    */
    @PostMapping("/lots")
    ResponseEntity<Lot> newLot(@RequestBody Lot lot) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(lot));
    }

    /* curl sample :
    curl -i -X PUT localhost:8080/lots/2 ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"description\": \"test description\", \"pictureUrl\": \"https://picsum.photos/id/237/600/400\", \"startPrice\": "12.95", \"startDate\": \"2025-04-25 12:30\", \"startDate\": \"2025-04-30 12:30\", \"category\": null, \"status\": \"ACTIVATED\" }"
     */
    @PutMapping("/lots/{id}")
    Lot replaceLot(@RequestBody Lot newLot, @PathVariable Long id) {
        return repository.findById(id)
                .map(lot -> {
                    lot.setName(newLot.getName());
                    lot.setDescription(newLot.getDescription());
                    lot.setPictureUrl(newLot.getPictureUrl());
                    lot.setStartPrice(newLot.getStartPrice());
                    lot.setCurrentPrice(newLot.getCurrentPrice());
                    lot.setStartDate(newLot.getStartDate());
                    lot.setEndDate(newLot.getEndDate());
                    lot.setCategory(newLot.getCategory());
                    lot.setStatus(newLot.getStatus());
                    lot.setTags(newLot.getTags());
                    return repository.save(lot);
                })
                .orElseGet(() -> {
                    newLot.setId(id);
                    return repository.save(newLot);
                });
    }

    /* curl sample :
    curl -i -X DELETE localhost:8080/lots/2
    */
    @DeleteMapping("/lots/{id}")
    ResponseEntity<String> deleteLot(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new LotNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Lot deleted");
    }
}
