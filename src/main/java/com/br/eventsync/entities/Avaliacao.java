package com.br.eventsync.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Evento evento;
    @ManyToOne
    private Usuario usuario;
    private Integer nota;
    private String comentario;
    private LocalDateTime dataHoraAvaliacao;
}
