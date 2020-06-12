package com.esgi.stats19.api.common.entities;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="soccer_cross")
public class Cross {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer crossId;
    private Integer elapsed;
    private Integer elapsedPlus;
    private String type;
    private Integer eventIncidentTypefk;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_match_player_id")
    private TeamMatchPlayer teamMatchPlayer;
}
