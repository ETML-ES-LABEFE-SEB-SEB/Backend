package ch.etmles.Backend.Tag;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getTagsFromString(List<String> stringTags)
    {
        List<Tag> tags = new ArrayList<>();
        for(String stringTag : stringTags)
        {
            Optional<Tag> tag = tagRepository.findByLabel(stringTag);

            if(tag.isPresent() && !tags.contains(tag.get()))
                tags.add(tag.get());
        }
        return tags;
    }
}
