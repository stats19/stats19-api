package com.esgi.stats19.api.matches.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeamDTO {
    @Nullable
    private Integer teamApiId;
    @Nullable
    private Integer teamFifaId;
    @Nullable
    private String name;
    private String shortName;
}
