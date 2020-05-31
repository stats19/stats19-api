package com.esgi.stats19.api.common.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE bey_website SET disabled=true WHERE bet_website_id=?")
@Where(clause = "disabled = false")
public class BetWebsite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer betWebsiteId;

    @NotNull
    @Column(length = 45)
    @Size(min = 2, max = 45)
    private String name;

    @NotNull
    @Column(length = 45)
    @Size(min = 2, max = 45)
    private String url;

    private Boolean display = true;
    private Boolean disabled = false;

    @OneToMany(mappedBy = "betWebsite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MatchBet> bets;
}
