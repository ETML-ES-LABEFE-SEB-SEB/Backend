package ch.etmles.payroll.Lot;

import ch.etmles.payroll.Member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LotRepository extends JpaRepository<Lot, UUID> {

    Lot findByName(String name);

    Page<Lot> findByOwner(Member currentUser, PageRequest endDate);
}