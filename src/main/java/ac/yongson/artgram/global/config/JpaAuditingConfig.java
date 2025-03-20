package ac.yongson.artgram.global.config;

import ac.yongson.artgram.global.timestamp.Timestamped;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.time.ZonedDateTime;
import java.util.Optional;

@Configuration
@EnableScheduling
@EnableJpaAuditing(dateTimeProviderRef = "zonedDateTimeProvider")
public class JpaAuditingConfig {

    @Bean
    public DateTimeProvider zonedDateTimeProvider(){
        return () -> Optional.of(ZonedDateTime.now(Timestamped.SEOUL_ZONE_ID));
    }
}
