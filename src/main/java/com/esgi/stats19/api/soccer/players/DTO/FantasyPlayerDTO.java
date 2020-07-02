package com.esgi.stats19.api.soccer.players.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FantasyPlayerDTO {
    private Integer playerId;
    private String name;
    private String picture;
    private String leagueName;
    private Integer leagueId;
    private Double score;
    private String teamName;
    private Integer teamId;
}
