package com.esgi.stats19.api.common.entities;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_match_player")
public class TeamMatchPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamMatchPlayerId;

    @Column(name = "position_x")
    private Integer positionX;

    @Column(name = "position_y")
    private Integer positionY;
    @NotNull
    @ColumnDefault("true")
    private boolean firstTeam;

    private Double score;



    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "player_api_id")
    private Player player;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_match_id")
    private TeamMatch teamMatch;

    @OneToMany(mappedBy = "teamMatchPlayer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Corner> corners;

    @OneToMany(mappedBy = "scorer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Shot> scored;
    @OneToMany(mappedBy = "assist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Shot> assisted;

    @OneToMany(mappedBy = "teamMatchPlayer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cross> crosses;

    @OneToMany(mappedBy = "culprit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Foul> culprits;
    @OneToMany(mappedBy = "victim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Foul> victims;
}
