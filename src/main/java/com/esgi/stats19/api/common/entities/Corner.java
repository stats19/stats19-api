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

    @Column(nullable = true)
    private int elapsedPlus;
    private String type;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_match_player_id")
    private TeamMatchPlayer teamMatchPlayer;
}
