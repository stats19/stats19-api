package com.esgi.stats19.api.soccer.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamMatchFormatted {
    private Integer teamId;
    private String picture;
    private String name;
    private Integer goals;
    private Integer possession;
    private Integer shotOnTarget;
    private Integer fouls;
    private Integer yellowCards;
    private Integer redCards;
}
