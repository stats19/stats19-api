package com.esgi.stats19.api.soccer.leagues.DTO;

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
public class GetLeagueMatch {
    private Integer matchId;
    private String homeName;
    private String homePicture;
    private String awayPicture;
    private String awayName;
    private Winner resultMatch;
    private Integer step;
    private Date date;
}
