package ch.etmles.Backend.Lot;

import ch.etmles.Backend.Bid.DTO.BidDTO;
import ch.etmles.Backend.ListApiResponse;
import ch.etmles.Backend.Lot.DTO.AddLotDTO;
import ch.etmles.Backend.Lot.DTO.LotDTO;
import ch.etmles.Backend.Lot.Exceptions.LotNotFoundException;
import ch.etmles.Backend.LotCategory.Category;
import ch.etmles.Backend.LotCategory.Exceptions.CategoryNotFoundException;
import ch.etmles.Backend.LotCategory.CategoryService;
import ch.etmles.Backend.Member.MemberService;
import ch.etmles.Backend.SingleApiResponse;
import ch.etmles.Backend.Tag.TagService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/lots")
public class LotController {

    private final LotRepository repository;
    private final LotService lotService;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final TagService tagService;

    public LotController(LotRepository repository, LotService lotService, CategoryService categoryService, MemberService memberService, TagService tagService) {
        this.repository = repository;
        this.lotService = lotService;
        this.categoryService = categoryService;
        this.memberService = memberService;
        this.tagService = tagService;
    }

    /* curl sample :
    curl -i localhost:8080/lots
    */
    @GetMapping("")
    ListApiResponse<LotDTO> getLots(@RequestParam(defaultValue = "") UUID categoryId, @RequestParam(defaultValue = "1") int page) {
        if (page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, 12, Sort.by(Sort.Direction.ASC, "endDate"));

        if (categoryId == null)
            return new ListApiResponse<LotDTO>(repository.findByStatus(LotStatus.ACTIVATED, pageable).map(LotDTO::toDto));

        List<Category> categories = categoryService.getCategoryChainFromId(categoryId);
        if (categories.isEmpty())
            throw new CategoryNotFoundException(categoryId);

        return new ListApiResponse<LotDTO>(repository.findByStatusAndCategoryIn(LotStatus.ACTIVATED, categories, pageable).map(LotDTO::toDto));
    }

    /* curl sample :
    curl -i localhost:8080/lots/1
    */
    @GetMapping("{id}")
    SingleApiResponse<LotDTO> one(@PathVariable UUID id) {
        Optional<Lot> toFind = repository.findById(id);
        if(toFind.isEmpty())
            throw new LotNotFoundException(id);
        return new SingleApiResponse<LotDTO>(LotDTO.toDto(toFind.get()));
    }

    /* curl sample :
    curl -i localhost:8080/lots/1
    */
    @GetMapping("{id}/history")
    ListApiResponse<BidDTO> history(@PathVariable UUID id) {
        return new ListApiResponse<BidDTO>(lotService.getBidsForLot(id));
    }

    /* curl sample :
    curl -i -X POST localhost:8080/lots ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"description\": \"test description\", \"pictureUrl\": \"https://picsum.photos/id/237/600/400\", \"startPrice\": "12.95", \"startDate\": \"2025-04-25 12:30\", \"startDate\": \"2025-04-30 12:30\", \"category\": null, \"status\": \"ACTIVATED\" }"
    */
    @PostMapping("")
    ResponseEntity<SingleApiResponse<LotDTO>> newLot(@RequestBody AddLotDTO lot) {
        Lot newLot = new Lot();
        newLot.setDescription(lot.getDescription());
        newLot.setStartPrice(lot.getStartPrice());
        newLot.setCurrentPrice(lot.getStartPrice());
        newLot.setStartDate(LocalDateTime.now());
        newLot.setEndDate(lot.getEndDate());
        newLot.setCategory(categoryService.getCategoryFromId(lot.categoryId));
        newLot.setOwner(memberService.getCurrentMember());
        newLot.setTags(tagService.getTagsFromString(lot.getTags()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new SingleApiResponse<LotDTO>(LotDTO.toDto(repository.save(newLot))));
    }

    /* curl sample :
    curl -i -X PUT localhost:8080/lots/2 ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"description\": \"test description\", \"pictureUrl\": \"https://picsum.photos/id/237/600/400\", \"startPrice\": "12.95", \"startDate\": \"2025-04-25 12:30\", \"startDate\": \"2025-04-30 12:30\", \"category\": null, \"status\": \"ACTIVATED\" }"
     */
    @PutMapping("{id}")
    SingleApiResponse<LotDTO> replaceLot(@RequestBody Lot newLot, @PathVariable UUID id) {
        return new SingleApiResponse<LotDTO>(LotDTO.toDto(repository.findById(id)
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
                })));
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
