package com.esgi.stats19.api.common.entities;

import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchBetId;
    private Double home;
    private Double away;
    private Double draw;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id")
    private Match match;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bet_website_id")
    private BetWebsite betWebsite;
}
