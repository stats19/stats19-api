package com.esgi.stats19.api.soccer.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchDetailsFormattedDTO {
    private Integer playerId;
    private String playerName;
    private Integer elapsed;
    private Integer elapsedPlus;
    private Boolean home;
    private Integer type;
}
