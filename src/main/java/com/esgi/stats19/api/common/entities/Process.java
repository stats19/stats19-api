package com.esgi.stats19.api.common.entities;

import com.esgi.stats19.api.common.enums.ProcessStatus;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Stats19Process")
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer processId;
    @NotNull
    @Column(unique = true)
    private String processName;
    @Enumerated(EnumType.ORDINAL)
    private ProcessStatus status;

    public Process(String processName, ProcessStatus processStatus){
        this.processName = processName;
        this.status = processStatus;
    }

    public Process() { }
}
