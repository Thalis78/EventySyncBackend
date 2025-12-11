package com.br.eventsync.dtos.request;

import lombok.Data;

@Data
public class MensagemRequestDTO {
    private Integer remetenteId;
    private Integer destinatarioId;
    private String conteudo;
}