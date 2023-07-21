package com.delta.commerce.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_historic")
public class Historic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historic_id")
    private Long historicOrderId;

    @Column(name = "historic_entity")
    private String historicEntity;

    @Column(name = "historic_entity_id")
    private Long historicEntityId;

    @Column(name = "historic_description")
    private String historicDescription;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;



}