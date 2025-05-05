package ch.etmles.payroll.LotCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LotCategoryRepository extends JpaRepository<LotCategory, UUID> {

}
