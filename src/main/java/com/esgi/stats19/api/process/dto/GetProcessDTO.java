package com.esgi.stats19.api.process.dto;

import com.esgi.stats19.api.common.enums.ProcessStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProcessDTO {
    private Integer processId;
    private String processName;
    private ProcessStatus status;
}
