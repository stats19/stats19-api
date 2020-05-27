package com.esgi.stats19.api.teams.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
