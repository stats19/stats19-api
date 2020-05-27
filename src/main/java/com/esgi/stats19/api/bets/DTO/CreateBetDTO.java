package com.esgi.stats19.api.bets.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBetDTO {
    @NotNull
    @Size(min=1)
    private Integer betWebsiteId;
    @NotNull
    @Size(min=1)
    private Integer matchId;
    @NotNull
    @Size(min=1)
    private Double home;
    @NotNull
    @Size(min=1)
    private Double draw;
    @NotNull
    @Size(min=1)
    private Double away;
}
