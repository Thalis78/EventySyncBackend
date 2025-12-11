package com.br.eventsync.entities;

import com.br.eventsync.entities.constantes.StatusAmizade;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Amizade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Usuario solicitante;
    @ManyToOne
    private Usuario destinatario;
    @ManyToOne
    private Mensagem mensagem;
    private StatusAmizade statusAmizade;
    private LocalDateTime dataHoraSolicitacao;
}
