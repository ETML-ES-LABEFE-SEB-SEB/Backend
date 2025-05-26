package ch.etmles.Backend;

import ch.etmles.Backend.Lot.Lot;
import ch.etmles.Backend.Lot.LotRepository;
import ch.etmles.Backend.Lot.LotService;
import ch.etmles.Backend.Lot.LotStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CloseLotScheduledTask {

    private final LotService lotService;

    public CloseLotScheduledTask(LotService lotService) {
        this.lotService = lotService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void closeLots() {
        System.out.println("Closing lots");
        List<Lot> lotsToClose = lotService.getExpiredActivatedLots();
        for (Lot lot : lotsToClose) {
            lotService.closeLot(lot.getId());
        }
        System.out.println("End closing lots");
    }
}
