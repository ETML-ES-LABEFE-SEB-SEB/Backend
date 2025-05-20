package ch.etmles.payroll.LotCategory;

import ch.etmles.payroll.LotCategory.Exceptions.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    /* curl sample :
    curl -i localhost:8080/categories
    */
    @GetMapping("")
    List<Category> all() {
        return repository.findAll();
    }

    /* curl sample :
    curl -i localhost:8080/categories/1
    */
    @GetMapping("{id}")
    Category one(@PathVariable UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    /* curl sample :
        curl -i -X POST localhost:8080/categories ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"parent\": null }"
    */
    @PostMapping("")
    ResponseEntity<Category> newLotCategory(@RequestBody Category category) {
        category.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(category));
    }

    /* curl sample :
        curl -i -X PUT localhost:8080/categories/2 ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"parent\": null }"
    */
    @PutMapping("{id}")
    Category replaceLotCategory(@RequestBody Category newCategory, @PathVariable UUID id) {
        return repository.findById(id)
                .map(lotCategory -> {
                    lotCategory.setName(newCategory.getName());
                    lotCategory.setParent(newCategory.getParent());
                    return repository.save(lotCategory);
                })
                .orElseGet(() -> {
                    newCategory.setId(id);
                    return repository.save(newCategory);
                });
    }

    /* curl sample :
        curl -i -X DELETE localhost:8080/categories/2
    */
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteLotCategory(@PathVariable UUID id) {
        if (!repository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("LotCategory deleted");
    }
}
