package com.esgi.stats19.api.common.entities;

import com.esgi.stats19.api.common.enums.Winner;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="soccer_match")
public class Match implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchId;

    @NotNull
    @Column(name = "match_api_id")
    private Integer matchApiId;
    @NotNull
    private Integer stage;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;
    @NotNull
    private String season;
    @NotNull
    private boolean played;
    @NotNull
    @ColumnDefault("false")
    private Boolean scoreCalculated;

    @Enumerated(EnumType.ORDINAL)
    private Winner forecast;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "league_id")
    private League league;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatchBet> bets;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamMatch> teamMatches;
}
