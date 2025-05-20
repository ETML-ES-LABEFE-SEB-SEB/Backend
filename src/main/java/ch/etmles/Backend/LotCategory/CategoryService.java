package ch.etmles.Backend.LotCategory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoryFromId(UUID categoryId)
    {
        Optional<Category> category = categoryRepository.findById(categoryId);
        List<Category> childCategories = categoryRepository.findCategoriesByParent(category.get());
        childCategories.add(category.get());
        return childCategories;
    }
}
