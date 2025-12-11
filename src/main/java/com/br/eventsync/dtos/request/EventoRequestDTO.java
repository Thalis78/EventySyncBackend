package com.br.eventsync.dtos.request;
import com.br.eventsync.entities.constantes.StatusEvento;
import com.br.eventsync.entities.constantes.TipoEvento;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoRequestDTO {

    private Integer organizadorId;
    private String titulo;
    private String descricao;
    private String localEndereco;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFinal;
    private double preco;
    private TipoEvento tipoEvento;
    private LocalDateTime inscricaoAbre;
    private LocalDateTime inscricaoFecha;
    private Integer maxInscricao;
    private StatusEvento statusEvento;
    private Integer cargaHoraria;
    private boolean inscricaoAberta;
}
