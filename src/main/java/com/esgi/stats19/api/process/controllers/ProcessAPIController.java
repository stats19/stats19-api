package com.esgi.stats19.api.process.controllers;

import com.esgi.stats19.api.common.enums.ProcessStatus;
import com.esgi.stats19.api.process.dto.GetProcessStatusDTO;
import com.esgi.stats19.api.process.services.ProcessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/process")
public class ProcessAPIController {
    private final ProcessService processService;

    public ProcessAPIController(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping("{processName}")
    public GetProcessStatusDTO checkProcess(@PathVariable String processName) {
        var process = processService.getProcess(processName);
        if (process == null) {
            return new GetProcessStatusDTO(ProcessStatus.NO_STATUS, true);
        }

        var retry = process.getStatus() != ProcessStatus.FAILED && process.getStatus() != ProcessStatus.ENDED;
        return new GetProcessStatusDTO(process.getStatus(), retry);
    }
}
