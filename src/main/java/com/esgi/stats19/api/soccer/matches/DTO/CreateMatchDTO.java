package com.esgi.stats19.api.soccer.matches.DTO;

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
public class CreateMatchDTO {
    @NotNull
    @Size(min=1)
    private Integer teamApiId;
    @NotNull
    @Size(min=1)
    private Integer teamFifaId;
    @NotNull
    @Size(min=2, max=45)
    private String name;
    @NotNull
    @Size(min=2, max=45)
    private String shortName;
}
