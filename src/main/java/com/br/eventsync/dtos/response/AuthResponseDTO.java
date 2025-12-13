package com.br.eventsync.dtos.response;

import com.br.eventsync.entities.constantes.PapelUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private PapelUsuario papelUsuario;
    private Integer idUsuario;
    private String token;
}
