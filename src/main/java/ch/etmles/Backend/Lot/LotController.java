package ch.etmles.Backend.Lot;

import ch.etmles.Backend.Base64DecodedMultipartFile;
import ch.etmles.Backend.Bid.DTO.BidDTO;
import ch.etmles.Backend.ResponseAPI.*;
import ch.etmles.Backend.Lot.DTO.LotSearchDTO;
import ch.etmles.Backend.LotCategory.Category;
import ch.etmles.Backend.LotCategory.DTO.CategoryDTO;
import ch.etmles.Backend.Lot.DTO.AddLotDTO;
import ch.etmles.Backend.Lot.DTO.LotDTO;
import ch.etmles.Backend.Lot.Exceptions.LotNotFoundException;
import ch.etmles.Backend.Lot.Exceptions.OrderByNotFoundException;
import ch.etmles.Backend.LotCategory.CategoryService;
import ch.etmles.Backend.Member.MemberService;
import ch.etmles.Backend.Tag.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

import static ch.etmles.Backend.apiVersion.API_VERSION;

@RestController
@RequestMapping("/" + API_VERSION + "/lots")
public class LotController {

    private final LotRepository repository;
    private final LotService lotService;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final TagService tagService;
    private final LotRepository lotRepository;
    private final AwsS3Service awsS3Service;

    public LotController(LotRepository repository, LotService lotService, CategoryService categoryService, MemberService memberService, TagService tagService, LotRepository lotRepository, AwsS3Service awsS3Service) {
        this.repository = repository;
        this.lotService = lotService;
        this.categoryService = categoryService;
        this.memberService = memberService;
        this.tagService = tagService;
        this.lotRepository = lotRepository;
        this.awsS3Service = awsS3Service;
    }

    /* curl sample :
    curl -i localhost:8080/lots
    */
    @GetMapping("")
    SinglePageApiResponse<LotSearchDTO> getLots(
            @RequestParam(required = false) SortOptions orderBy,
            @RequestParam(defaultValue = "") UUID categoryId,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam(defaultValue = "0") double maxPrice,
            @RequestParam(defaultValue = "1") int page) {

        if (page < 1) page = 1;
        int pageSize = 12;

        List<Lot> lots = repository.findByStatus(LotStatus.ACTIVATED);

        // Filter category
        if(categoryId != null)
            lots = lots.stream().filter(lot -> lot.getCategory().getId().equals(categoryId)).toList();

        // Filter name
        if(!name.isBlank())
            lots = lots.stream().filter(lot -> lot.getName().toLowerCase().contains(name.toLowerCase())).toList();

        // Min and max current price
        if(minPrice >= 0)
            lots = lots.stream().filter(lot -> lot.getCurrentPrice().doubleValue() >= minPrice).toList();
        if(maxPrice > 0)
            lots = lots.stream().filter(lot -> lot.getCurrentPrice().doubleValue() <= maxPrice).toList();


        // Sort by
        if(orderBy != null)
            switch (orderBy) {
                case NAME_ASC -> lots = lots.stream().sorted(Comparator.comparing(Lot::getName)).toList();
                case NAME_DESC -> lots = lots.stream().sorted(Comparator.comparing(Lot::getName).reversed()).toList();
                case POSTED_AT -> lots = lots.stream().sorted(Comparator.comparing(Lot::getStartDate)).toList();
                case FINISH_AT -> lots = lots.stream().sorted(Comparator.comparing(Lot::getEndDate)).toList();
                case PRICE_ASC -> lots = lots.stream().sorted(Comparator.comparing(Lot::getCurrentPrice)).toList();
                case PRICE_DESC -> lots = lots.stream().sorted(Comparator.comparing(Lot::getCurrentPrice).reversed()).toList();
                default -> throw new OrderByNotFoundException(orderBy.toString());
            }
        else
            lots = lots.stream().sorted(Comparator.comparing(Lot::getEndDate)).toList();

        // Pagination Infos
        int totalElements = lots.size();
        int totalPages = (int)Math.ceil(lots.size() / pageSize);
        boolean isLastPage = totalPages == page;


        // Paginated
        int fromIndex = Math.max(0, (page - 1) * pageSize);
        int toIndex = Math.min(fromIndex + pageSize, lots.size());
        List<LotDTO> pagedLots = fromIndex >= toIndex ? List.of() : lots.subList(fromIndex, toIndex).stream().map(LotDTO::toDto).toList();
        CategoryDTO categoryDTO = null;
        if(categoryId != null){
            Category category = categoryService.getCategoryFromId(categoryId);
            if(category != null)
                categoryDTO = CategoryDTO.toDTO(category);
        }

        return new SinglePageApiResponse<>(LotSearchDTO.toLotSearchDTO(pagedLots, categoryDTO), new PaginationInfo(page, pageSize, totalElements, totalPages, isLastPage));
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
    ListPageApiResponse<BidDTO> history(@PathVariable UUID id) {
        return new ListPageApiResponse<BidDTO>(lotService.getBidsForLot(id));
    }

    @GetMapping("sort-options")
    ListApiResponse<String> sortOptions() {
        List<String> options = Arrays.stream(SortOptions.values())
                .map(Enum::name)
                .toList();
        return new ListApiResponse<>(options);
    }

    /* curl sample :
    curl -i -X POST localhost:8080/lots ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"description\": \"test description\", \"pictureUrl\": \"https://picsum.photos/id/237/600/400\", \"startPrice\": "12.95", \"startDate\": \"2025-04-25 12:30\", \"startDate\": \"2025-04-30 12:30\", \"category\": null, \"status\": \"ACTIVATED\" }"
    */
    @PostMapping("")
    ResponseEntity<SingleApiResponse<LotDTO>> newLot(@RequestBody AddLotDTO lot) {

        Lot newLot = new Lot();

        // Upload picture
        if(lot.pictureContent.isEmpty())
            newLot.setPictureUrl("https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/no_picture.png");
        else
        {
            String[] parts = lot.pictureContent.split(",");
            String metadata = parts[0];
            String base64Data = parts[1];
            String contentType = metadata.substring(metadata.indexOf(":") + 1, metadata.indexOf(";"));
            byte[] decoded = Base64.getDecoder().decode(base64Data);
            MultipartFile convertedPicture = new Base64DecodedMultipartFile(decoded, contentType);

            newLot.setPictureUrl(awsS3Service.saveFileToBucket(convertedPicture));
        }


        newLot.setName(lot.getName());
        newLot.setDescription(lot.getDescription());
        newLot.setStartPrice(lot.getStartPrice());
        newLot.setCurrentPrice(lot.getStartPrice());
        newLot.setStartDate(lot.getStartDate().isAfter(LocalDateTime.now()) ? lot.getStartDate() : LocalDateTime.now()); // Don't allow Date < Now
        newLot.setEndDate(lot.getEndDate());
        newLot.setCategory(categoryService.getCategoryFromId(lot.categoryId));
        newLot.setOwner(memberService.getCurrentMember());
        newLot.setTags(tagService.getTagsFromString(lot.getTags()));
        newLot.setStatus(LotStatus.ACTIVATED); // By default -> Activated
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

    @PostMapping("{id}/close")
    ResponseEntity<String> closeLot(@PathVariable UUID id) {

        if(lotRepository.findById(id).isEmpty())
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Lot: " + id + " not found");
            throw new LotNotFoundException(id);

        if(lotService.closeLot(id))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Lot closed");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not able to close lot");
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
