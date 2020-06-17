package com.esgi.stats19.api.soccer.leagues.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingItem {
    private Integer teamId;
    private String name;
    private int matchPlayed;
    private int win;
    private int draw;
    private int lose;
    private int score;
    private int points;
}
