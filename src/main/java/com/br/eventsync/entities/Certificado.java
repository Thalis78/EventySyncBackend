package com.br.eventsync.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Evento evento;

    @ManyToOne
    private Usuario participante;

    private LocalDateTime dataHoraEmissao;
}
