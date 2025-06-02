package ch.etmles.Backend.LotCategory;

import ch.etmles.Backend.PageApiResponse;
import ch.etmles.Backend.LotCategory.DTO.CategoryDTO;
import ch.etmles.Backend.LotCategory.Exceptions.CategoryNotFoundException;
import ch.etmles.Backend.SingleApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ch.etmles.Backend.apiVersion.API_VERSION;

@RestController
@RequestMapping("/" + API_VERSION + "/categories")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    /* curl sample :
    curl -i localhost:8080/categories
    */
    @GetMapping("")
    PageApiResponse<CategoryDTO> all() {
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        List<Category> categories = repository.findAll();
        for(Category category : categories)
            categoryDTOs.add(CategoryDTO.toDTO(category));
        return new PageApiResponse<CategoryDTO>(categoryDTOs);
    }

    /* curl sample :
    curl -i localhost:8080/categories/1
    */
    @GetMapping("{id}")
    SingleApiResponse<CategoryDTO> one(@PathVariable UUID id) {
        return new SingleApiResponse<>(CategoryDTO.toDTO(repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id))));
    }

    /* curl sample :
        curl -i -X POST localhost:8080/categories ^
        -H "Content-type:application/json" ^
        -d "{\"name\": \"test\", \"parent\": null }"
    */
    @PostMapping("")
    ResponseEntity<CategoryDTO> newLotCategory(@RequestBody Category category) {
        category.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryDTO.toDTO(repository.save(category)));
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
