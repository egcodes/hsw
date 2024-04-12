package com.hackerupdates.hsw.domain.entity;

import java.io.Serial;
import java.io.Serializable;
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

@Entity
@Table(indexes = {
    @Index(name="activity_personId", columnList="personId")
})
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Activity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2241699815447690916L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long personId;

    @NotNull
    @Setter
    private Long lastActivityTime;

}
