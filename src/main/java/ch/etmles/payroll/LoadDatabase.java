package ch.etmles.payroll;

import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(LotRepository repository){
        return args->{
            log.info("Preloading " + repository.save(new Lot("Lot Test 1", "Test description 1", new BigDecimal("19.95"))));
            log.info("Preloading " + repository.save(new Lot("Lot Test 2", "Test description 2", new BigDecimal("15"))));
        };
    }
}
