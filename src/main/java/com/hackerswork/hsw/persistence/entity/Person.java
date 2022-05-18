package com.hackerswork.hsw.persistence.entity;

import com.hackerswork.hsw.enums.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Person implements Serializable {

    private static final long serialVersionUID = 5581667625001993382L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String firstName;

    @Column(length = 50)
    @NotNull
    private String lastName;

    @Column(length = 50)
    @NotNull
    private String userName;

    @Column(length = 50)
    @NotNull
    private String mail;

    @NotNull
    @Setter
    private Status status;

    @NotNull
    @Setter
    private OffsetDateTime createDate;

}
