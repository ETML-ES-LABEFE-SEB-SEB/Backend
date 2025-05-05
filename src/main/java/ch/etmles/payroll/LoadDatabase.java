package ch.etmles.payroll;

import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotRepository;
import ch.etmles.payroll.Lot.LotStatus;
import ch.etmles.payroll.LotCategory.LotCategory;
import ch.etmles.payroll.LotCategory.LotCategoryRepository;
import ch.etmles.payroll.Tag.Tag;
import ch.etmles.payroll.Tag.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(LotRepository lotRepository, LotCategoryRepository categoryRepository, TagRepository tagRepository){
        LotCategory parentCategory = new LotCategory("Musique", null);
        LotCategory childCategory = new LotCategory("Guitare", parentCategory);
        Tag fenderTag = new Tag("Fender");
        List<Tag> tags = new ArrayList<>();
        tags.add(fenderTag);
        return args->{
            log.info("Preloading " + tagRepository.save(fenderTag));
            log.info("Preloading " + categoryRepository.save(parentCategory));
            log.info("Preloading " + categoryRepository.save(childCategory));
            log.info("Preloading " + lotRepository.save(new Lot("Lot Test 1", "Test description 1", "https://picsum.photos/id/237/600/400", new BigDecimal("19.95"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), parentCategory, LotStatus.ACTIVATED,tags)));
            log.info("Preloading " + lotRepository.save(new Lot("Lot Test 2", "Test description 2", "https://picsum.photos/id/238/600/400", new BigDecimal("49.95"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), childCategory, LotStatus.ACTIVATED,tags)));
            log.info("Swagger : http://localhost:8080/swagger-ui/index.html");
        };
    }
}
