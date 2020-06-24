package com.esgi.stats19.api.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;
    @NotNull
    @Size(min=1)
    @Column(name = "player_api_id")
    private Integer playerApiId;
    @NotNull
    @Size(min=1, max=100)
    private String name;
    private Integer player_fifa_api_id;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Integer height;
    private Integer weight;
    private Double scoreAverage;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamMatchPlayer> teamsMatchesPlayers;
}
