package ch.etmles.payroll.Lot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LotRepository extends JpaRepository<Lot, UUID> {

}