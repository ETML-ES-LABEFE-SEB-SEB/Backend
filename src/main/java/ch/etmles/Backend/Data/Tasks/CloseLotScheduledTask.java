package ch.etmles.Backend.Data.Tasks;

import ch.etmles.Backend.LoadDatabase;
import ch.etmles.Backend.Lot.Lot;
import ch.etmles.Backend.Lot.LotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CloseLotScheduledTask {

    private final LotService lotService;
    private final static Logger logger = LoggerFactory.getLogger(LoadDatabase .class);

    public CloseLotScheduledTask(LotService lotService) {
        this.lotService = lotService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void closeLots() {
        logger.info("Checking for expired lots...");
        List<Lot> lotsToClose = lotService.getExpiredLots();
        for (Lot lot : lotsToClose) {
            lotService.closeLot(lot.getId());
        }
        logger.info(lotsToClose.size() + " lots have been closed...");
    }
}
