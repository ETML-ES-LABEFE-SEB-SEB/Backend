package ch.etmles.Backend.Lot;

import ch.etmles.Backend.Bid.DTO.BidDTO;
import ch.etmles.Backend.Lot.Exceptions.LotNotFoundException;
import ch.etmles.Backend.LotCategory.Category;
import ch.etmles.Backend.LotCategory.Exceptions.CategoryNotFoundException;
import ch.etmles.Backend.LotCategory.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lots")
public class LotController {

    private final LotRepository repository;
    private final LotService lotService;
    private final CategoryService categoryService;

    public LotController(LotRepository repository, LotService lotService, CategoryService categoryService) {
        this.repository = repository;
        this.lotService = lotService;
        this.categoryService = categoryService;
    }

    /* curl sample :
    curl -i localhost:8080/lots
    */
    @GetMapping("")
    Page<Lot> getLots(@RequestParam(defaultValue = "") UUID categoryId, @RequestParam(defaultValue = "1") int page) {
        if (page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, 12, Sort.by(Sort.Direction.ASC, "endDate"));

        if (categoryId == null)
            return repository.findAll(pageable);

        List<Category> categories = categoryService.getCategoryFromId(categoryId);
        if (categories.isEmpty())
            throw new CategoryNotFoundException(categoryId);

        return repository.findByCategoryIn(categories, pageable);
    }

    /* curl sample :
    curl -i localhost:8080/lots/1
    */
    @GetMapping("{id}")
    Lot one(@PathVariable UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new LotNotFoundException(id));
    }

    /* curl sample :
    curl -i localhost:8080/lots/1
    */
    @GetMapping("{id}/history")
    List<BidDTO> history(@PathVariable UUID id) {
        return lotService.getBidsForLot(id);
    }

    /* curl sample :
    curl -i -X POST localhost:8080/lots ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"description\": \"test description\", \"pictureUrl\": \"https://picsum.photos/id/237/600/400\", \"startPrice\": "12.95", \"startDate\": \"2025-04-25 12:30\", \"startDate\": \"2025-04-30 12:30\", \"category\": null, \"status\": \"ACTIVATED\" }"
    */
    @PostMapping("")
    ResponseEntity<Lot> newLot(@RequestBody Lot lot) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(lot));
    }

    /* curl sample :
    curl -i -X PUT localhost:8080/lots/2 ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"description\": \"test description\", \"pictureUrl\": \"https://picsum.photos/id/237/600/400\", \"startPrice\": "12.95", \"startDate\": \"2025-04-25 12:30\", \"startDate\": \"2025-04-30 12:30\", \"category\": null, \"status\": \"ACTIVATED\" }"
     */
    @PutMapping("{id}")
    Lot replaceLot(@RequestBody Lot newLot, @PathVariable UUID id) {
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
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteLot(@PathVariable UUID id) {
        if (!repository.existsById(id)) {
            throw new LotNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Lot deleted");
    }
}
