package com.br.eventsync.dtos.request;
import com.br.eventsync.entities.constantes.PapelUsuario;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String nome;
    private String email;
    private String senha;
    private String cidade;
    private PapelUsuario papelUsuario;
}
