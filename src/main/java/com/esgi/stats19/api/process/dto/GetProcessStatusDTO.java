package com.esgi.stats19.api.process.dto;

import com.esgi.stats19.api.common.enums.ProcessStatus;
import lombok.Data;

@Data
public class GetProcessStatusDTO {
    private ProcessStatus status;
    private boolean retry;

    public GetProcessStatusDTO(ProcessStatus status, boolean retry) {
        this.status = status;
        this.retry = retry;
    }
}
