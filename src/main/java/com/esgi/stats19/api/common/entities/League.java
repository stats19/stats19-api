package com.esgi.stats19.api.common.entities;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer leagueId;
    private String picture;

    @NotNull
    @Column(length = 45)
    @Size(min = 2, max = 45)
    private String name;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> matches;
}
