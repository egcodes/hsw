package com.hackerswork.hsw.persistence.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
