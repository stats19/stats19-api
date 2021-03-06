package com.esgi.stats19.api.soccer.leagues.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLeagueDTO {
    private Integer leagueId;
    private String picture;
    private String name;
    private String country;
    private String matches;
    private String teams;
}
