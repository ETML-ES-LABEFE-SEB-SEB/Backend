package ch.etmles.payroll.Bid;

import ch.etmles.payroll.Lot.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BidRepository extends JpaRepository<Bid, UUID> {

    List<Bid> findByBidUpLot(Lot lot);
}
