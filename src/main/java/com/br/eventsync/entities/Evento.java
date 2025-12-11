package com.br.eventsync.entities;

import com.br.eventsync.entities.constantes.StatusEvento;
import com.br.eventsync.entities.constantes.TipoEvento;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;  // Import necessário
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties({"inscricoes", "organizador"})
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Usuario organizador;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Inscricao> inscricoes = new ArrayList<>();

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
