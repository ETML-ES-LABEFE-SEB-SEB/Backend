package ch.etmles.Backend.Lot;

import ch.etmles.Backend.LotCategory.Category;
import ch.etmles.Backend.Member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LotRepository extends JpaRepository<Lot, UUID> {

    Page<Lot> findByOwner(Member currentUser, PageRequest endDate);
    Page<Lot> findByStatus(LotStatus status, Pageable pageable);
    Page<Lot> findByCategoryIn(List<Category> categories, Pageable pageable);
    Page<Lot> findByStatusAndCategoryIn(LotStatus status, List<Category> categories, Pageable pageable);

    List<Lot> getLotByStatusAndEndDateBefore(LotStatus status, LocalDateTime endDateBefore);

    List<Lot> getLotByStatus(LotStatus status);
}