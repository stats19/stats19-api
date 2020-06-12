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
    private Integer elapsed;
    private Integer elapsedPlus;
    private String type;
    private Integer eventIncidentTypefk;
    
    @Enumerated(EnumType.ORDINAL)
    private Card card;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "culprit_id")
    private TeamMatchPlayer culprit;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "victim_id")
    private TeamMatchPlayer victim;
}
