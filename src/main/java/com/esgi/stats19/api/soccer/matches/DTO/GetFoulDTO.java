package com.esgi.stats19.api.soccer.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetFoulDTO {
    private Integer foulId;

    private String culpritName;
    private String culprit;
    private String victimName;
    private String victim;

    private int elapsed;
    private int elapsedPlus;
    private String type;
    private String card;
}
