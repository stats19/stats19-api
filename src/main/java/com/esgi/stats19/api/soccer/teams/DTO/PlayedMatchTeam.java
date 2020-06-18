package com.esgi.stats19.api.soccer.teams.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayedMatchTeam {
    private Integer teamId;
    private String name;
    private Integer goals;
}
