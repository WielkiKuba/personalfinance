package io.github.wielkikuba.personalfinance.house;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.wielkikuba.personalfinance.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "house")
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner",nullable = false)
    private User owner;
}
