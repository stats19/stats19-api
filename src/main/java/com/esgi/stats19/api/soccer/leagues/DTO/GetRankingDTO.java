package com.esgi.stats19.api.soccer.leagues.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRankingDTO {
    private int leagueId;
    private String leagueName;
    private String season;
    private List<RankingItem> rankingItems;
}
