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
@Table(name= "team_match")
public class TeamMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamMatchId;

    @NotNull
    private int goals;
    @NotNull
    private boolean home;
    @NotNull
    @Size(max= 100)
    private int possession;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "team_api_id")
    private Team team;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", referencedColumnName = "match_api_id")
    private Match match;

    @OneToMany(mappedBy = "teamMatch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamMatchPlayer> teamsMatchesPlayers;
}
