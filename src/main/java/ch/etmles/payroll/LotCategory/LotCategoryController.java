package ch.etmles.payroll.LotCategory;

import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotNotFoundException;
import ch.etmles.payroll.Lot.LotRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class LotCategoryController {

    private final LotCategoryRepository repository;

    public LotCategoryController(LotCategoryRepository lotCategoryRepository) {
        this.repository = lotCategoryRepository;
    }

    /* curl sample :
    curl -i localhost:8080/lotcategories
    */
    @GetMapping("/lotcategories")
    List<LotCategory> all() {
        return repository.findAll();
    }

    /* curl sample :
    curl -i localhost:8080/lotcategories/1
    */
    @GetMapping("/lotcategories/{id}")
    LotCategory one(@PathVariable UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new LotCategoryNotFoundException(id));
    }

    /* curl sample :
        curl -i -X POST localhost:8080/lotcategories ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"parent\": null }"
    */
    @PostMapping("/lotcategories")
    ResponseEntity<LotCategory> newLotCategory(@RequestBody LotCategory lotCategory) {
        lotCategory.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(lotCategory));
    }

    /* curl sample :
        curl -i -X PUT localhost:8080/lotcategories/2 ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"parent\": null }"
    */
    @PutMapping("/lotcategories/{id}")
    LotCategory replaceLotCategory(@RequestBody LotCategory newLotCategory, @PathVariable UUID id) {
        return repository.findById(id)
                .map(lotCategory -> {
                    lotCategory.setName(newLotCategory.getName());
                    lotCategory.setParent(newLotCategory.getParent());
                    return repository.save(lotCategory);
                })
                .orElseGet(() -> {
                    newLotCategory.setId(id);
                    return repository.save(newLotCategory);
                });
    }

    /* curl sample :
        curl -i -X DELETE localhost:8080/lotcategories/2
    */
    @DeleteMapping("/lotcategories/{id}")
    ResponseEntity<String> deleteLotCategory(@PathVariable UUID id) {
        if (!repository.existsById(id)) {
            throw new LotCategoryNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("LotCategory deleted");
    }
}
