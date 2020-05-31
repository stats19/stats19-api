package com.esgi.stats19.api.common.entities;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Corner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cornerId;
    private int elapsed;
    private int elapsedPlus;
    private String type;
    private int eventIncidentTypefk;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_match_player_id")
    private TeamMatchPlayer teamMatchPlayer;
}
