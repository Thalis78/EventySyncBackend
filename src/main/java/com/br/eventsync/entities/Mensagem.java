package com.br.eventsync.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Usuario remetente;
    @ManyToOne
    private Usuario destinatario;
    private LocalDateTime dataHoraMensagem;
    private String conteudo;
}
