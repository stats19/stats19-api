package com.esgi.stats19.api.soccer.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetShotDTO {
    private Integer shotId;

    private String scorerName;
    private String shooter;
    private String assistName;
    private String assist;

    private Integer elapsed;
    private Integer elapsedPlus;
    private String type;
    private String goalType;
    private boolean scored;
    private boolean onTarget;
}
