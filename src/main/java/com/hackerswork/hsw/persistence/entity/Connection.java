package com.hackerswork.hsw.persistence.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
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

    private boolean hidden;

    private boolean blocked;

    private boolean pinned;
}
