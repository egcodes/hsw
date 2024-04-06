package com.hackerupdates.hsw.persistence.entity;

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
    @Index(name="connection_personId", columnList="personId")
})
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Connection implements Serializable {

    private static final long serialVersionUID = 1858746340416571229L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long personId;

    @NotNull
    private Long connectionId;

    @Setter
    private boolean hidden;

    @Setter
    private boolean blocked;

    @Setter
    private boolean pinned;
}
