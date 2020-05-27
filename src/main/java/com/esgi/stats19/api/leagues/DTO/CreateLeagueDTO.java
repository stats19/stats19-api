package com.esgi.stats19.api.leagues.DTO;

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
public class CreateLeagueDTO {
    @NotNull
    @Size(min=1)
    private Integer countryId;
    @NotNull
    private String name;
}
