package com.br.eventsync.dtos.response;

import com.br.eventsync.entities.constantes.StatusInscricao;
import com.br.eventsync.entities.constantes.TipoEvento;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InscricaoEventoResponseDTO {

    private Integer id;
    private Integer idInscrito;
    private String tituloEvento;
    private StatusInscricao statusInscricao;
    private LocalDateTime dataHoraInscricao;
    private Integer cargaHoraria;
    private TipoEvento tipoEvento;
}
