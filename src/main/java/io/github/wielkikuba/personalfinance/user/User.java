package io.github.wielkikuba.personalfinance.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.wielkikuba.personalfinance.house.House;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "surname",nullable = false)
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("owner")
    @JoinColumn(name = "house")
    private House house;
}
