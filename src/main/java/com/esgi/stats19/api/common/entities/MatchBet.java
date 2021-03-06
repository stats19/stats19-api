package com.esgi.stats19.api.common.entities;

import lombok.*;
import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "match_bet")
public class MatchBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matchBetId;
    private Double home;
    private Double away;
    private Double draw;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id", referencedColumnName = "match_api_id")
    private Match match;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bet_website_id")
    private BetWebsite betWebsite;
}
