package com.esgi.stats19.api.soccer.teams.DTO;

import com.esgi.stats19.api.common.enums.ResultMatch;
import com.esgi.stats19.api.common.enums.Winner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayedMatch {
    private Integer matchId;
    private Date date;
    private ResultMatch resultMatch;
    private Winner forecast;
    private PlayedMatchTeam home;
    private PlayedMatchTeam away;
}
