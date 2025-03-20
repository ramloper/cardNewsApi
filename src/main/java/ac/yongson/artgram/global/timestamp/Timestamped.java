package ac.yongson.artgram.global.timestamp;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
public abstract class Timestamped{
    public static final ZoneId SEOUL_ZONE_ID = ZoneId.of("Asia/Seoul");
    private ZonedDateTime createdDateTime;
    private ZonedDateTime updatedDateTime;
    @PrePersist
    public void prePersist(){
        this.createdDateTime = ZonedDateTime.now();
        this.updatedDateTime = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedDateTime = ZonedDateTime.now();
    }
}
