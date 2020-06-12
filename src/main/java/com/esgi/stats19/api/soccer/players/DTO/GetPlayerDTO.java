package com.esgi.stats19.api.soccer.players.DTO;

import com.esgi.stats19.api.common.entities.TeamMatchPlayer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerDTO {
    private Integer playerId;
    private String name;
    private String birthday;
    private Integer height;
    private Integer weight;
    private String matches;
}
