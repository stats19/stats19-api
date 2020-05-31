package com.esgi.stats19.api.common.entities;

import com.esgi.stats19.api.common.enums.Card;
import lombok.*;
import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Foul {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer foulId;
    private int elapsed;
    private int elapsedPlus;
    private String type;
    private int eventIncidentTypefk;
    
    @Enumerated(EnumType.ORDINAL)
    private Card card;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "culprit_id")
    private TeamMatchPlayer culprit;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "victim_id")
    private TeamMatchPlayer victim;
}
