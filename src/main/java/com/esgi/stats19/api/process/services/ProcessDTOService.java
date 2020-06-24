package com.esgi.stats19.api.process.services;

import com.esgi.stats19.api.common.entities.Process;
import com.esgi.stats19.api.process.dto.GetProcessDTO;
import org.springframework.stereotype.Service;

@Service
public class ProcessDTOService {
    public GetProcessDTO toResponse(Process process) {
        return GetProcessDTO.builder()
                .processId(process.getProcessId())
                .processName(process.getProcessName())
                .status(process.getStatus())
                .build();
    }
}
