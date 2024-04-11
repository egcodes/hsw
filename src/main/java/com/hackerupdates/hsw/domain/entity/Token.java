package com.hackerupdates.hsw.domain.entity;

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
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString
@Table(indexes = {
    @Index(name="token_token", columnList="token")
})
public class Token implements Serializable {

    private static final long serialVersionUID = 3018776368057112848L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userName;

    @NotNull
    private Long personId;

    @Setter
    @NotNull
    @Length(max = 255)
    private String token;

    @Setter
    private Long expireDate;
}
