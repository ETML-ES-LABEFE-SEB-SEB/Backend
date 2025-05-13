package ch.etmles.payroll;

import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotRepository;
import ch.etmles.payroll.Lot.LotStatus;
import ch.etmles.payroll.LotCategory.Category;
import ch.etmles.payroll.LotCategory.CategoryRepository;
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
    CommandLineRunner initDatabase(LotRepository lotRepository, CategoryRepository categoryRepository, TagRepository tagRepository){
        Category parentCategory = new Category("Musique", null);
        Category childCategory = new Category("Guitare", parentCategory);
        Category carCategory = new Category("Voiture", null);
        Category photoCategory = new Category("Photo", null);
        Category reflexCategory = new Category("Appareil photo", photoCategory);
        Category gamingCategory = new Category("Gaming", null);
        Category controllerCategory = new Category("Controller", gamingCategory);
        Tag rareTag = new Tag("Rare");
        Tag gibsonTag = new Tag("Gibson");
        Tag consoleTag = new Tag("PS3");
        List<Tag> guitareTags = new ArrayList<>();
        guitareTags.add(gibsonTag);
        List<Tag> photoTags = new ArrayList<>();
        photoTags.add(rareTag);
        List<Tag> gamingTags = new ArrayList<>();
        gamingTags.add(consoleTag);
        return args->{
            log.info("Preloading " + tagRepository.save(rareTag));
            log.info("Preloading " + tagRepository.save(gibsonTag));
            log.info("Preloading " + tagRepository.save(consoleTag));
            log.info("Preloading " + categoryRepository.save(parentCategory));
            log.info("Preloading " + categoryRepository.save(childCategory));
            log.info("Preloading " + categoryRepository.save(carCategory));
            log.info("Preloading " + categoryRepository.save(photoCategory));
            log.info("Preloading " + categoryRepository.save(reflexCategory));
            log.info("Preloading " + categoryRepository.save(gamingCategory));
            log.info("Preloading " + categoryRepository.save(controllerCategory));
            log.info("Preloading " + lotRepository.save(new Lot("Voiture de collection", "Vend voiture de collection américaine retro en très bon état.", "https://picsum.photos/id/111/600/400", new BigDecimal("48000"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).minusDays(2).toString(), carCategory, LotStatus.ACTIVATED,photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Guitare", "Guitare Gibson, très bon état. Produite en 1978 aux USA.", "https://picsum.photos/id/145/600/400", new BigDecimal("399.95"), LocalDateTime.now().minusDays(3).toString(), (LocalDateTime.now()).plusDays(5).toString(), childCategory, LotStatus.ACTIVATED,guitareTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Appareil photo", "Bon appareil photo argentique, objectif en bon état. Très bien pour un rendu classique.", "https://picsum.photos/id/250/600/400", new BigDecimal("650"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), reflexCategory, LotStatus.ACTIVATED, photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Controlleur PS3", "Controlleur pour la console PS3 en bon état, couleur noir.", "https://picsum.photos/id/96/600/400", new BigDecimal("45"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusHours(8).toString(), controllerCategory, LotStatus.ACTIVATED, gamingTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Voiture de collection", "Vend voiture de collection américaine retro en très bon état.", "https://picsum.photos/id/111/600/400", new BigDecimal("48000"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusDays(2).toString(), carCategory, LotStatus.ACTIVATED,photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Guitare", "Guitare Gibson, très bon état. Produite en 1978 aux USA.", "https://picsum.photos/id/145/600/400", new BigDecimal("399.95"), LocalDateTime.now().minusDays(3).toString(), (LocalDateTime.now()).plusDays(5).toString(), childCategory, LotStatus.ACTIVATED,guitareTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Appareil photo", "Bon appareil photo argentique, objectif en bon état. Très bien pour un rendu classique.", "https://picsum.photos/id/250/600/400", new BigDecimal("650"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), reflexCategory, LotStatus.ACTIVATED, photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Controlleur PS3", "Controlleur pour la console PS3 en bon état, couleur noir.", "https://picsum.photos/id/96/600/400", new BigDecimal("45"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusHours(8).toString(), controllerCategory, LotStatus.ACTIVATED, gamingTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Voiture de collection", "Vend voiture de collection américaine retro en très bon état.", "https://picsum.photos/id/111/600/400", new BigDecimal("48000"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusDays(2).toString(), carCategory, LotStatus.ACTIVATED,photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Guitare", "Guitare Gibson, très bon état. Produite en 1978 aux USA.", "https://picsum.photos/id/145/600/400", new BigDecimal("399.95"), LocalDateTime.now().minusDays(3).toString(), (LocalDateTime.now()).plusDays(5).toString(), childCategory, LotStatus.ACTIVATED,guitareTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Appareil photo", "Bon appareil photo argentique, objectif en bon état. Très bien pour un rendu classique.", "https://picsum.photos/id/250/600/400", new BigDecimal("650"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), reflexCategory, LotStatus.ACTIVATED, photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Controlleur PS3", "Controlleur pour la console PS3 en bon état, couleur noir.", "https://picsum.photos/id/96/600/400", new BigDecimal("45"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusHours(8).toString(), controllerCategory, LotStatus.ACTIVATED, gamingTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Voiture de collection", "Vend voiture de collection américaine retro en très bon état.", "https://picsum.photos/id/111/600/400", new BigDecimal("48000"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusDays(2).toString(), carCategory, LotStatus.ACTIVATED,photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Guitare", "Guitare Gibson, très bon état. Produite en 1978 aux USA.", "https://picsum.photos/id/145/600/400", new BigDecimal("399.95"), LocalDateTime.now().minusDays(3).toString(), (LocalDateTime.now()).plusDays(5).toString(), childCategory, LotStatus.ACTIVATED,guitareTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Appareil photo", "Bon appareil photo argentique, objectif en bon état. Très bien pour un rendu classique.", "https://picsum.photos/id/250/600/400", new BigDecimal("650"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), reflexCategory, LotStatus.ACTIVATED, photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Controlleur PS3", "Controlleur pour la console PS3 en bon état, couleur noir.", "https://picsum.photos/id/96/600/400", new BigDecimal("45"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusHours(8).toString(), controllerCategory, LotStatus.ACTIVATED, gamingTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Voiture de collection", "Vend voiture de collection américaine retro en très bon état.", "https://picsum.photos/id/111/600/400", new BigDecimal("48000"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusDays(2).toString(), carCategory, LotStatus.ACTIVATED,photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Guitare", "Guitare Gibson, très bon état. Produite en 1978 aux USA.", "https://picsum.photos/id/145/600/400", new BigDecimal("399.95"), LocalDateTime.now().minusDays(3).toString(), (LocalDateTime.now()).plusDays(5).toString(), childCategory, LotStatus.ACTIVATED,guitareTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Appareil photo", "Bon appareil photo argentique, objectif en bon état. Très bien pour un rendu classique.", "https://picsum.photos/id/250/600/400", new BigDecimal("650"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), reflexCategory, LotStatus.ACTIVATED, photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Controlleur PS3", "Controlleur pour la console PS3 en bon état, couleur noir.", "https://picsum.photos/id/96/600/400", new BigDecimal("45"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusHours(8).toString(), controllerCategory, LotStatus.ACTIVATED, gamingTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Voiture de collection", "Vend voiture de collection américaine retro en très bon état.", "https://picsum.photos/id/111/600/400", new BigDecimal("48000"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusDays(2).toString(), carCategory, LotStatus.ACTIVATED,photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Guitare", "Guitare Gibson, très bon état. Produite en 1978 aux USA.", "https://picsum.photos/id/145/600/400", new BigDecimal("399.95"), LocalDateTime.now().minusDays(3).toString(), (LocalDateTime.now()).plusDays(5).toString(), childCategory, LotStatus.ACTIVATED,guitareTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Appareil photo", "Bon appareil photo argentique, objectif en bon état. Très bien pour un rendu classique.", "https://picsum.photos/id/250/600/400", new BigDecimal("650"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), reflexCategory, LotStatus.ACTIVATED, photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Controlleur PS3", "Controlleur pour la console PS3 en bon état, couleur noir.", "https://picsum.photos/id/96/600/400", new BigDecimal("45"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusHours(8).toString(), controllerCategory, LotStatus.ACTIVATED, gamingTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Voiture de collection", "Vend voiture de collection américaine retro en très bon état.", "https://picsum.photos/id/111/600/400", new BigDecimal("48000"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusDays(2).toString(), carCategory, LotStatus.ACTIVATED,photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Guitare", "Guitare Gibson, très bon état. Produite en 1978 aux USA.", "https://picsum.photos/id/145/600/400", new BigDecimal("399.95"), LocalDateTime.now().minusDays(3).toString(), (LocalDateTime.now()).plusDays(5).toString(), childCategory, LotStatus.ACTIVATED,guitareTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Appareil photo", "Bon appareil photo argentique, objectif en bon état. Très bien pour un rendu classique.", "https://picsum.photos/id/250/600/400", new BigDecimal("650"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), reflexCategory, LotStatus.ACTIVATED, photoTags)));
            log.info("Preloading " + lotRepository.save(new Lot("Controlleur PS3", "Controlleur pour la console PS3 en bon état, couleur noir.", "https://picsum.photos/id/96/600/400", new BigDecimal("45"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusHours(8).toString(), controllerCategory, LotStatus.ACTIVATED, gamingTags)));

            log.info("Swagger : http://localhost:8080/swagger-ui/index.html");
        };
    }
}
