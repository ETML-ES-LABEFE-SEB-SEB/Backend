package ch.etmles.Backend.Bid;

import ch.etmles.Backend.Lot.Lot;
import ch.etmles.Backend.Member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BidRepository extends JpaRepository<Bid, UUID> {

    List<Bid> findByBidUpLot(Lot lot);
    List<Bid> findByOwner(Member owner);
    Page<Bid> findByOwner(Member owner, Pageable pageable);
}
