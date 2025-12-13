package com.br.eventsync.entities;

import com.br.eventsync.entities.constantes.StatusInscricao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    @JsonIgnoreProperties({"inscricoes"})
    private Evento evento;
    @ManyToOne
    private Usuario usuario;

    private StatusInscricao statusInscricao;
    private LocalDateTime dataHoraInscricao;
    private LocalDateTime dataHoraPagamento;
    private boolean certificadoEmitido;

}
