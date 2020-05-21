package com.esgi.stats19.api.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetWebsite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer betWebsiteId;

    @NotNull
    @Column(length = 45)
    @Size(min = 2, max = 45)
    private String name;

    @OneToMany(mappedBy = "betWebsite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatchBet> bets;
}
