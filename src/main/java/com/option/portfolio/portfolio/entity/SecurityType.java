package com.option.portfolio.portfolio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "security_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "version",  columnDefinition = "Long DEFAULT 0")
    @Version
    @JsonIgnore
    @Builder.Default
    Long version  = 0L;

    @Column(name = "type", nullable = false, unique = true)
    String type;

    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name="last_updated", nullable = false)
    LocalDateTime lastUpdated;
}
