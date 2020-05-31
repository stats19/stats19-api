package com.esgi.stats19.api.soccer.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamMatchDTO {
    private Integer teamId;
    private String team;
    private String name;
    private String players;
    private Integer goals;
    private Integer possession;
    private boolean home;
}
