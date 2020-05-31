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
    private String scorer;
    private String assistName;
    private String assist;

    private int elapsed;
    private int elapsedPlus;
    private String type;
    private String goalType;
    private boolean scored;
    private boolean onTarget;
}
