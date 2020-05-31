package com.esgi.stats19.api.soccer.teams.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamDTO {
    private Integer teamId;
    private int teamApiId;
    private int teamFifaId;
    private String name;
    private String shortName;
    private String matches;
}
