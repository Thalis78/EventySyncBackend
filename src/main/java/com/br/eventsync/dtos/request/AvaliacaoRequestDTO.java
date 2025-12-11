package com.br.eventsync.dtos.request;

import lombok.Data;

@Data
public class AvaliacaoRequestDTO {

    private Integer usuarioId;
    private int nota;
    private String comentario;
}
