package ch.etmles.payroll;

import ch.etmles.payroll.Bid.Bid;
import ch.etmles.payroll.Bid.BidRepository;
import ch.etmles.payroll.Lot.Lot;
import ch.etmles.payroll.Lot.LotRepository;
import ch.etmles.payroll.Lot.LotStatus;
import ch.etmles.payroll.LotCategory.Category;
import ch.etmles.payroll.LotCategory.CategoryRepository;
import ch.etmles.payroll.Member.Member;
import ch.etmles.payroll.Member.MemberRepository;
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
    CommandLineRunner initDatabase(LotRepository lotRepository, CategoryRepository categoryRepository, TagRepository tagRepository, MemberRepository memberRepository, BidRepository bidRepository){
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
        Member memberNo1 = new Member("Tartempion", "https://picsum.photos/id/103/200", new BigDecimal("15000"), new BigDecimal("0"), new ArrayList<>());
        Member memberNo2 = new Member("Toutankhamon", "https://picsum.photos/id/64/200", new BigDecimal("250.45"), new BigDecimal("650"), new ArrayList<>());

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
            log.info("Preloading " + memberRepository.save(memberNo1));
            log.info("Preloading " + memberRepository.save(memberNo2));

            Member connectedMember = memberRepository.findByUsername("Tartempion");
            Member owner = memberRepository.findByUsername("Toutankhamon");

            Lot photoCamWithBids = new Lot("Appareil photo avec une enchère", "Bon appareil photo argentique, objectif en bon état. Très bien pour un rendu classique.", "https://picsum.photos/id/250/600/400", new BigDecimal("650"), LocalDateTime.now().minusDays(14).toString(), (LocalDateTime.now()).minusDays(7).toString(), reflexCategory, LotStatus.ACTIVATED, photoTags, owner);
            photoCamWithBids.setCurrentPrice(BigDecimal.valueOf(660));
            log.info("Preloading " + lotRepository.save(photoCamWithBids));

            log.info("Preloading " + bidRepository.save(new Bid(new BigDecimal("650"), LocalDateTime.now().minusDays(1), photoCamWithBids, connectedMember)));
            log.info("Preloading " + bidRepository.save(new Bid(new BigDecimal("655"), LocalDateTime.now(), photoCamWithBids, connectedMember)));

            log.info("Preloading " + lotRepository.save(new Lot("Voiture de collection", "Vend voiture de collection américaine retro en très bon état.", "https://picsum.photos/id/111/600/400", new BigDecimal("48000"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).minusDays(2).toString(), carCategory, LotStatus.ACTIVATED,photoTags, connectedMember)));
            for(int i = 0; i < 10; ++i) {
                log.info("Preloading " + lotRepository.save(new Lot("Voiture de collection", "Vend voiture de collection américaine retro en très bon état.", "https://picsum.photos/id/111/600/400", new BigDecimal("48000"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).minusDays(2).toString(), carCategory, LotStatus.ACTIVATED,photoTags, i == 0 ? owner : null)));
                log.info("Preloading " + lotRepository.save(new Lot("Guitare", "Guitare Gibson, très bon état. Produite en 1978 aux USA.", "https://picsum.photos/id/145/600/400", new BigDecimal("399.95"), LocalDateTime.now().minusDays(3).toString(), (LocalDateTime.now()).plusDays(5).toString(), childCategory, LotStatus.ACTIVATED,guitareTags, i == 0 ? owner : null)));
                log.info("Preloading " + lotRepository.save(new Lot("Controlleur PS3", "Controlleur pour la console PS3 en bon état, couleur noir.", "https://picsum.photos/id/96/600/400", new BigDecimal("45"), LocalDateTime.now().minusDays(5).toString(), (LocalDateTime.now()).plusHours(8).toString(), controllerCategory, LotStatus.ACTIVATED, gamingTags, i == 0 ? owner : null)));
                log.info("Preloading " + lotRepository.save(new Lot("Appareil photo", "Bon appareil photo argentique, objectif en bon état. Très bien pour un rendu classique.", "https://picsum.photos/id/250/600/400", new BigDecimal("650"), LocalDateTime.now().toString(), (LocalDateTime.now()).plusDays(7).toString(), reflexCategory, LotStatus.ACTIVATED, photoTags, i == 0 ? owner : null)));
            }
            log.info("Swagger : http://localhost:8080/swagger-ui/index.html");
        };
    }
}
