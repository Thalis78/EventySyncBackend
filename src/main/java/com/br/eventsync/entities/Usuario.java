package com.br.eventsync.entities;

import com.br.eventsync.entities.constantes.PapelUsuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String hashSenha;
    private String cidade;
    private String urlFoto;
    private boolean visibilidadeParticipacao;
    private PapelUsuario papelUsuario;
}
