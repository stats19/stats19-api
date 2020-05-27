package com.esgi.stats19.api.bets.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetDTO {
    private Integer teamId;
    private String teamName;
    private String team;
    private Double odds;
}
