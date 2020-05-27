package com.esgi.stats19.api.countries.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCountryDTO {
    private Integer countryId;
    private String name;
    private String matches;
    private String leagues;
    private String players;
}
