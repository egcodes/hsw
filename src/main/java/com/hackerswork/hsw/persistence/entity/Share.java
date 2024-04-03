package com.hackerswork.hsw.persistence.entity;

import java.io.Serializable;
import java.time.Instant;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(indexes = {
    @Index(name="share_personId", columnList="personId")
})
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Share implements Serializable {

    private static final long serialVersionUID = -445626973360837740L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long personId;

    @NotNull
    @Setter
    private Instant createdTime;

    @NotNull
    @Length(min = 10, max = 280)
    private String text;

}
