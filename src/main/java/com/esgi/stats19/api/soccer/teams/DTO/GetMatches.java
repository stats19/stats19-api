package com.esgi.stats19.api.soccer.teams.DTO;
import com.esgi.stats19.api.common.entities.TeamMatch;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetMatches {
    List<TeamMatch> wins = new ArrayList<>();
    List<TeamMatch> draw = new ArrayList<>();
    List<TeamMatch> lose = new ArrayList<>();
}
