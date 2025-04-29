package ch.etmles.payroll;

import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotRepository;
import ch.etmles.payroll.Lot.LotStatus;
import ch.etmles.payroll.LotCategory.LotCategory;
import ch.etmles.payroll.LotCategory.LotCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(LotRepository repository, LotCategoryRepository categoryRepository){
        LotCategory parentCategory = new LotCategory("Musique", null);
        LotCategory childCategory = new LotCategory("Guitare", parentCategory);
        return args->{
            log.info("Preloading " + categoryRepository.save(parentCategory));
            log.info("Preloading " + categoryRepository.save(childCategory));
            log.info("Preloading " + repository.save(new Lot("Lot Test 1", "Test description 1", "https://picsum.photos/id/237/600/400", new BigDecimal("19.95"), LocalDateTime.now(), (LocalDateTime.now()).plusDays(7), parentCategory, LotStatus.ACTIVATED)));
            log.info("Preloading " + repository.save(new Lot("Lot Test 2", "Test description 2", "https://picsum.photos/id/238/600/400", new BigDecimal("49.95"), LocalDateTime.now(), (LocalDateTime.now()).plusDays(7), childCategory, LotStatus.ACTIVATED)));
        };
    }
}
