package com.esgi.stats19.api.bets.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBetWebsiteDTO {
    @NotNull
    @Size(min=1)
    private String websiteName;
    @NotNull
    @Size(min=1)
    private String websiteUrl;
}
