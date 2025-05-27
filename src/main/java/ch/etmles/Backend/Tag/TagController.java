package ch.etmles.Backend.Tag;

import ch.etmles.Backend.ListApiResponse;
import ch.etmles.Backend.SingleApiResponse;
import ch.etmles.Backend.Tag.DTO.TagDTO;
import ch.etmles.Backend.Tag.Exceptions.TagNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ch.etmles.Backend.apiVersion.API_VERSION;

@RestController
@RequestMapping("/" + API_VERSION + "/tags")
public class TagController {

    private final TagRepository repository;

    public TagController(TagRepository tagRepository) {
        this.repository = tagRepository;
    }

    /* curl sample :
    curl -i localhost:8080/tags
    */
    @GetMapping("")
    ListApiResponse<TagDTO> all() {
        List<Tag> tags = repository.findAll();
        List<TagDTO> tagDTOs = new ArrayList<>();
        for (Tag tag : tags)
            tagDTOs.add(TagDTO.toDto(tag));
        return new ListApiResponse<TagDTO>(tagDTOs);
    }

    /* curl sample :
    curl -i localhost:8080/tags/1
    */
    @GetMapping("{id}")
    SingleApiResponse<Tag> one(@PathVariable UUID id) {
        return new SingleApiResponse<>(repository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(id)));
    }
}
