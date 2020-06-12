package com.esgi.stats19.api.soccer.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTeamMatchPlayerDTO {
    private Integer playerId;
    private String name;
    private String player;
    private String teamName;
    private String team;
    private Integer positionX;
    private Integer positionY;
    private boolean firstTeam;

    private List<GetCornerDTO> corners;
    private List<GetCrossDTO> crosses;
    private List<GetFoulDTO> fouls;
    private List<GetShotDTO> shots;
    private List<GetShotDTO> assists;
}
