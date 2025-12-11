package com.br.eventsync.repositories;

import com.br.eventsync.entities.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    Optional<Avaliacao> findByEventoIdAndUsuarioId(Integer eventoId, Integer usuarioId);

}
