package ch.etmles.Backend.LotCategory;

import ch.etmles.Backend.LotCategory.DTO.CategoryHierarchyDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoryChainFromId(UUID categoryId)
    {
        Optional<Category> category = categoryRepository.findById(categoryId);
        List<Category> childCategories = categoryRepository.findCategoriesByParent(category.get());
        childCategories.add(category.get());
        return childCategories;
    }

    public Category getCategoryFromId(UUID categoryId)
    {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.orElse(null);
    }

    public List<CategoryHierarchyDTO> getCategoryHierarchy() {
        List<Category> allCategories = categoryRepository.findAll();

        // Map UUID -> DTO
        Map<UUID, CategoryHierarchyDTO> dtoMap = new HashMap<>();
        for (Category category : allCategories) {
            dtoMap.put(category.getId(), new CategoryHierarchyDTO(category.getId(), category.getName()));
        }

        // Construction de la hiérarchie
        List<CategoryHierarchyDTO> roots = new ArrayList<>();
        for (Category category : allCategories) {
            CategoryHierarchyDTO dto = dtoMap.get(category.getId());
            if (category.getParent() != null) {
                dtoMap.get(category.getParent().getId()).addChild(dto);
            } else {
                roots.add(dto);
            }
        }

        return roots;
    }
}
