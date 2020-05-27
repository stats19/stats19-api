package com.esgi.stats19.api.bets.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBetDTO {
    private Integer betId;
    private Integer matchId;
    private BetDTO home;
    private Double draw;
    private BetDTO away;
    private String match;
}
