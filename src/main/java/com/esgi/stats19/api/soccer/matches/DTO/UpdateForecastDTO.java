package com.esgi.stats19.api.soccer.matches.DTO;

import com.esgi.stats19.api.common.enums.Winner;
import lombok.Data;

@Data
public class UpdateForecastDTO {
    private Winner forecast;
}
