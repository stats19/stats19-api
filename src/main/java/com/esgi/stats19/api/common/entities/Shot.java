package com.esgi.stats19.api.common.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shotId;
    private Integer eventIncidentTypefk;
    private Integer elapsed;
    private Integer elapsedPlus;
    private String type;
    private String goalType;
    private boolean scored;
    private boolean onTarget;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shooter_id")
    private TeamMatchPlayer scorer;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "assist_id")
    private TeamMatchPlayer assist;
}
