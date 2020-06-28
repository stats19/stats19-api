package com.esgi.stats19.api.soccer.players.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetFantasyDTO {
    private String date;
    private List<FantasyPlayerDTO> goalKeepers;
    private List<FantasyPlayerDTO> defenders;
    private List<FantasyPlayerDTO> middleFielders;
    private List<FantasyPlayerDTO> forwards;
}

