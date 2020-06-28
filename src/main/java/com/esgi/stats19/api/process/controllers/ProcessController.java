package com.esgi.stats19.api.process.controllers;

import com.esgi.stats19.api.process.dto.GetProcessDTO;
import com.esgi.stats19.api.process.dto.UpdateStatusDTO;
import com.esgi.stats19.api.process.services.ProcessDTOService;
import com.esgi.stats19.api.process.services.ProcessService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process/update")
public class ProcessController {
    private final ProcessService processService;
    private final ProcessDTOService processDTOService;

    public ProcessController(ProcessService processService, ProcessDTOService processDTOService) {
        this.processService = processService;
        this.processDTOService = processDTOService;
    }

    @PostMapping("{processName}")
    public GetProcessDTO updateProcess(@RequestBody UpdateStatusDTO statusDTO, @PathVariable String processName) {
        return this.processDTOService.toResponse(processService.updateProcessStatus(processName, statusDTO.getStatus()));
    }
}
