package com.esgi.stats19.api.soccer.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCornerDTO {
    private Integer cornerId;

    private String playerName;
    private String player;

    private Integer elapsed;
    private Integer elapsedPlus;
    private String type;
}
