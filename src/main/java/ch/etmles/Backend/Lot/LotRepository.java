package ch.etmles.Backend.Lot;

import ch.etmles.Backend.LotCategory.Category;
import ch.etmles.Backend.Member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LotRepository extends JpaRepository<Lot, UUID> {

    Page<Lot> findByOwner(Member currentUser, PageRequest endDate);

    Page<Lot> findByCategoryIn(List<Category> categories, Pageable pageable);
}