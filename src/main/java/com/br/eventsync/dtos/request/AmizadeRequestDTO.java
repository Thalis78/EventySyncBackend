package com.br.eventsync.dtos.request;

import lombok.Data;

@Data
public class AmizadeRequestDTO {
    private Integer solicitanteId;
    private Integer destinatarioId;
    private Integer eventoId;
}