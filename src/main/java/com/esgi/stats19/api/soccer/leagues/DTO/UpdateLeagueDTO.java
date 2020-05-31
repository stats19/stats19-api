package com.esgi.stats19.api.soccer.leagues.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLeagueDTO {
    @Nullable
    private Integer countryId;
    @Nullable
    private String name;
}
