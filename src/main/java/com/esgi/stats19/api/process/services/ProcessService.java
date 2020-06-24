package com.esgi.stats19.api.process.services;

import com.esgi.stats19.api.common.entities.Process;
import com.esgi.stats19.api.common.enums.ProcessStatus;
import com.esgi.stats19.api.common.repositories.ProcessRepository;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {
    private final ProcessRepository processRepository;

    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    public Process getProcess(String processName) {
        return processRepository.findByProcessName(processName);
    }

    public Process updateProcessStatus(String processName, ProcessStatus status) {
        var process = getProcess(processName);
        if (process == null) {
            return processRepository.save(new Process(processName, status));
        }
        process.setStatus(status);
        return processRepository.save(process);
    }
}
