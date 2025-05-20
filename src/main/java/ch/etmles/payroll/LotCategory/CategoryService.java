package ch.etmles.payroll.LotCategory;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryFromId(UUID categoryId)
    {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.orElse(null);
    }
}
