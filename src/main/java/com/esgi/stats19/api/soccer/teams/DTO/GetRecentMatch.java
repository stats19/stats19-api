package com.esgi.stats19.api.soccer.teams.DTO;

import com.esgi.stats19.api.common.enums.ResultMatch;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetRecentMatch {
    private Integer matchId;
    private ResultMatch resultMatch;
}
