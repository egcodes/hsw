package com.hackerswork.hsw.persistence.entity;

import com.hackerswork.hsw.enums.Status;
import java.time.Instant;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Person implements Serializable {

    private static final long serialVersionUID = 5581667625001993382L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 50)
    @NotNull
    private String userName;

    @Length(max = 50)
    private String mail;

    @NotNull
    private Status status;

    @NotNull
    private Instant createDate;

    @Length(max = 50)
    private String name;

    @Length(max = 280)
    private String about;

    @ColumnDefault("false")
    private boolean darkTheme;
}
