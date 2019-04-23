package shorter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shorter.repo.InMemShortLinksRepo;
import shorter.repo.ShortLinksRepo;
import shorter.service.DefaultShortenLinkService;
import shorter.service.IdentShorterService;
import shorter.service.ShortenLinkService;
import shorter.service.ShorterService;

@Configuration
public class AppConfig {

    @Bean
    public ShortLinksRepo shortLinksRepo() {
        return new InMemShortLinksRepo();
    }

    @Bean
    public ShorterService shorterService() {
        return new IdentShorterService();
    }

    @Bean
    public ShortenLinkService shortenLinkService() {
        return new DefaultShortenLinkService(shortLinksRepo(), shorterService());
    }

}
