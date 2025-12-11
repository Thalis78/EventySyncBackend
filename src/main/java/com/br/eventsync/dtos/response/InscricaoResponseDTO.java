package com.br.eventsync.dtos.response;

import com.br.eventsync.entities.constantes.StatusInscricao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscricaoResponseDTO {
    private Integer id;
    private boolean certificadoEmitido;
    private StatusInscricao statusInscricao;
    private String dataHoraInscricao;
    private String nomeUsuario;


}