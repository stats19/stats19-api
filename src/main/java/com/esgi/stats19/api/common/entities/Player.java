package com.esgi.stats19.api.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;
    @NotNull
    @Size(min=1)
    @Column(name = "player_api_id")
    private int playerApiId;
    @NotNull
    @Size(min=1, max=100)
    private String name;
    private int player_fifa_api_id;
    private String birthday;
    private int height;
    private int weight;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamMatchPlayer> teamsMatchesPlayers;
}
