package com.esgi.stats19.api.soccer.players.DTO;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerDTO {
    private Integer playerId;
    private String name;
    private Integer age;
    private Integer height;
    private Integer weight;
    private Integer playedMatches;
    private Integer fouls;
    private Integer redCards;
    private Integer yellowCards;
    private Integer shotOnTarget;
    private Integer goals;
    private Double score;
    private String matches;
}
