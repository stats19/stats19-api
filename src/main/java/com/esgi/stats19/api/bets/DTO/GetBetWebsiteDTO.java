package com.esgi.stats19.api.bets.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBetWebsiteDTO {
    private Integer betWebsiteId;
    private String name;
    private String url;
    private String bets;
}
