package ch.etmles.Backend;

import ch.etmles.Backend.Bid.Bid;
import ch.etmles.Backend.Bid.BidRepository;
import ch.etmles.Backend.Lot.Lot;
import ch.etmles.Backend.Lot.LotRepository;
import ch.etmles.Backend.Lot.LotStatus;
import ch.etmles.Backend.LotCategory.Category;
import ch.etmles.Backend.LotCategory.CategoryRepository;
import ch.etmles.Backend.Member.Member;
import ch.etmles.Backend.Member.MemberRepository;
import ch.etmles.Backend.Member.MemberService;
import ch.etmles.Backend.Tag.Tag;
import ch.etmles.Backend.Tag.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(LotRepository lotRepository, CategoryRepository categoryRepository, TagRepository tagRepository, MemberRepository memberRepository, BidRepository bidRepository, MemberService memberService, PasswordEncoder passwordEncoder){

        return args -> {
            // ---------- Tags ----------
            Tag rareTag = new Tag("Rare");
            Tag gibsonTag = new Tag("Gibson");
            Tag fenderTag = new Tag("Fender");
            Tag ps3Tag = new Tag("PS3");
            Tag gamesTag = new Tag("Jeux-vidéo");
            Tag collectorTag = new Tag("Collector");
            Tag secondHandTag = new Tag("Occasion");
            tagRepository.saveAll(List.of(rareTag, gibsonTag, fenderTag, ps3Tag, gamesTag, collectorTag, secondHandTag));

            // ---------- Cegories ----------
            Category musique = new Category("Musique", null);
            Category guitare = new Category("Guitare", musique);
            Category voiture = new Category("Voiture", null);
            Category photo = new Category("Photo", null);
            Category reflex = new Category("Appareil photo", photo);
            Category gaming = new Category("Jeux-vidéo", null);
            Category controller = new Category("Controller", gaming);
            categoryRepository.saveAll(List.of(musique, guitare, voiture, photo, reflex, gaming, controller));

            // ---------- Members ----------
            Member member1 = createMember("Tartempion", "https://picsum.photos/id/103/200", "975", "1030", passwordEncoder);
            Member member2 = createMember("Toutankhamon", "https://picsum.photos/id/64/200", "5000", "25", passwordEncoder);
            Member member3 = createMember("Frangipanus", "https://picsum.photos/id/28/200", "30", "655", passwordEncoder);
            Member member4 = createMember("NoMoney", "https://picsum.photos/id/77/200", "0", "0", passwordEncoder);
            Member member5 = createMember("TooMuchMoney", "https://picsum.photos/id/44/200", "100000", "0", passwordEncoder);
            memberRepository.saveAll(List.of(member1, member2, member3, member4, member5));

            // ---------- tags for lots ----------
            List<Tag> rareTags = List.of(rareTag);
            List<Tag> gibsonTags = List.of(gibsonTag);
            List<Tag> fenderTags = List.of(fenderTag);
            List<Tag> collectorTags = List.of(collectorTag);
            List<Tag> ps3Tags = List.of(ps3Tag);
            List<Tag> gamesTags = List.of(gamesTag);
            List<Tag> emptyTags = List.of();
            List<Tag> voitureTags = List.of(secondHandTag);

            // ---------- base lots ----------
            Lot photoCam = new Lot("Appareil photo Canon",
                    "Bon appareil photo, objectif en bon état. Très bon rendu.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/appareil_photo_1.jpeg",
                    new BigDecimal("655"),
                    nowMinus(14), nowMinus(0),
                    reflex, LotStatus.ACTIVATED, rareTags, member2);
            lotRepository.save(photoCam);

            // Enchères
            bidRepository.save(new Bid(new BigDecimal("650"), LocalDateTime.now().minusDays(1), photoCam, member3));
            bidRepository.save(new Bid(new BigDecimal("655"), LocalDateTime.now().minusHours(2), photoCam, member1));


            // ---------- Lots supplémentaires ----------
            lotRepository.save(new Lot("Voiture de collection", "Vend voiture rétro en très bon état.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/voiture_collection.jpg", new BigDecimal("48000"), nowMinus(5), nowMinus(1), voiture, LotStatus.ACTIVATED, rareTags, member1));

            lotRepository.save(new Lot("Renault", "En bon état.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/renault.jpg", new BigDecimal("8950"), nowMinus(5), nowPlus(2), voiture, LotStatus.ACTIVATED, voitureTags, member1));

            lotRepository.save(new Lot("VW", "Elle roule.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/volkswagen.jpg", new BigDecimal("4400"), nowMinus(5), nowPlus(5), voiture, LotStatus.ACTIVATED, voitureTags, member4));

            lotRepository.save(new Lot("Fender Telecaster", "En super état.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/guitare_4.jpg", new BigDecimal("450"), nowMinus(5), nowPlus(3), guitare, LotStatus.ACTIVATED, fenderTags, member3));

            lotRepository.save(new Lot("Fender Stratocaster", "En bon état, RAS.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/guitare_3.jpg", new BigDecimal("690"), nowMinus(2), nowPlus(6), guitare, LotStatus.ACTIVATED, fenderTags, member5));

            lotRepository.save(new Lot("Guitare accoustique", "Son pas génial mais fait du bruit.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/guitare_2.jpg", new BigDecimal("120"), nowMinus(2), nowPlus(10), guitare, LotStatus.ACTIVATED, emptyTags, member4));

            lotRepository.save(new Lot("Zelda Ocarina of Time [N64]", "Bon état (voir photos), avec emballage d'origine.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/zelda_ocarina.jpg", new BigDecimal("220"), nowMinus(2), nowPlus(10), gaming, LotStatus.ACTIVATED, rareTags, member2));

            lotRepository.save(new Lot("Collector World of Warcraft", "Avec emballage d'origine, peu de traces d'utilisation",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/wow_collector.jpg", new BigDecimal("2200"), nowMinus(2), nowPlus(10), gaming, LotStatus.ACTIVATED, collectorTags, member1));

            lotRepository.save(new Lot("Collector Eve Online", "Avec emballage d'origine, vieille édition.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/eve_collector.jpg", new BigDecimal("950"), nowMinus(5), nowPlus(7), gaming, LotStatus.ACTIVATED, collectorTags, member3));

            // ---------- Boucle pour génération ----------
            for (int i = 0; i < 10; i++) {
                lotRepository.saveAll(List.of(
                        new Lot("Guitare Gibson vintage", "Gibson produite en 1978, très bon état.",
                                "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/guitare_5.jpg", new BigDecimal("399.95"),
                                nowMinus(3), nowPlus(25), guitare, LotStatus.ACTIVATED, gibsonTags, member2),

                        new Lot("Controlleur PS3", "Manette PS3 noire, fonctionne parfaitement.",
                                "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/ps3_controlleur.jpeg", new BigDecimal("45"),
                                nowMinus(5), nowPlus(26), controller, LotStatus.ACTIVATED, ps3Tags, member2),

                        new Lot("Appareil photo Nikon", "Très bon rendu.",
                                "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/appareil_photo_3.jpg", new BigDecimal("650"),
                                LocalDateTime.now().toString(), nowPlus(27), reflex, LotStatus.ACTIVATED, rareTags, member2),

                        new Lot("Audi", "Quasi neuve.",
                                "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/audi.jpg", new BigDecimal("29950"),
                                nowMinus(10), nowPlus(27), voiture, LotStatus.ACTIVATED, emptyTags, member5)
                ));
            }

            // ---------- Cas particuliers ----------

            // Lot sans tag
            lotRepository.save(new Lot("Controlleur Xbox", "Controlleur en bon état.", "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/xbox_controlleur.jpg",
                    new BigDecimal("10"), now(), nowPlus(1), controller, LotStatus.ACTIVATED, new ArrayList<>(), member1));

            // Sold lot
            Lot soldLot = lotRepository.save(new Lot("Controlleur PS3", "Super état.",
                    "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/ps3_controlleur.jpeg", new BigDecimal("25"), nowMinus(5), nowMinus(0), controller, LotStatus.ACTIVATED, ps3Tags, member1));
            bidRepository.saveAll(List.of(
                    new Bid(new BigDecimal("25"), nowMinusHours(10), soldLot, member2)
            ));

            // Multiple bids
            Lot lotWithMultipleBids = new Lot("Collector Eve Online", "Collector pour les 20 ans de Eve, en très bon état.", "https://s3.us-east-1.amazonaws.com/projlabefe.sebseb/eve_collector_2.jpg",
                    new BigDecimal("100"), nowMinus(1), nowPlus(2), gaming, LotStatus.ACTIVATED, collectorTags, member2);
            lotWithMultipleBids.setCurrentPrice(new BigDecimal("375"));
            lotRepository.save(lotWithMultipleBids);

            bidRepository.saveAll(List.of(
                    new Bid(new BigDecimal("360"), nowMinusHours(10), lotWithMultipleBids, member1),
                    new Bid(new BigDecimal("365"), nowMinusHours(9), lotWithMultipleBids, member3),
                    new Bid(new BigDecimal("370"), nowMinusHours(5), lotWithMultipleBids, member5),
                    new Bid(new BigDecimal("375"), nowMinusHours(1), lotWithMultipleBids, member1)
            ));
            log.info("Swagger : http://localhost:8080/swagger-ui/index.html");
        };
    }

    private Member createMember(String username, String imageUrl, String balance, String locked, PasswordEncoder encoder) {
        return new Member(username, encoder.encode("test"), imageUrl,
                new BigDecimal(balance), new BigDecimal(locked), new ArrayList<>());
    }

    private String now() {
        return LocalDateTime.now().toString();
    }

    private String nowMinus(long days) {
        return LocalDateTime.now().minusDays(days).toString();
    }

    private String nowPlus(long days) {
        return LocalDateTime.now().plusDays(days).toString();
    }

    private String nowPlusHours(long hours) {
        return LocalDateTime.now().plusHours(hours).toString();
    }

    private LocalDateTime nowMinusHours(long hours) {
        return LocalDateTime.now().minusHours(hours);
    }
}
