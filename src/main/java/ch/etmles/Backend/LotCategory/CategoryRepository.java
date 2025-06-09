package ch.etmles.Backend.LotCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByParent(Category parent);

    List<Category> findCategoriesByParent(Category parent);
}
