package com.delta.commerce.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;

@Entity(name = "RepairJob")
@Table(name = "tb_repair_job")
@Getter
@Setter
@NoArgsConstructor
public class RepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repair_job_id")
    private Long repairJobId;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "estimated_duration")
    private Duration estimatedDuration; // Duração estimada para a conclusão do trabalho

    @Column(name = "technician")
    private String technician; // Técnico responsável

}