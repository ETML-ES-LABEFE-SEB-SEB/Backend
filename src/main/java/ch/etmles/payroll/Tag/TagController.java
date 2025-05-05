package ch.etmles.payroll.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class TagController {

    private final TagRepository repository;

    public TagController(TagRepository tagRepository) {
        this.repository = tagRepository;
    }

    /* curl sample :
    curl -i localhost:8080/tags
    */
    @GetMapping("/tags")
    List<Tag> all() {
        return repository.findAll();
    }

    /* curl sample :
    curl -i localhost:8080/tags/1
    */
    @GetMapping("/tags/{id}")
    Tag one(@PathVariable UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id));
    }
}
