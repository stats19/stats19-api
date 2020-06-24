package com.esgi.stats19.api.process.dto;

import com.esgi.stats19.api.common.enums.ProcessStatus;
import lombok.Data;

@Data
public class UpdateStatusDTO {
    private ProcessStatus status;
}
