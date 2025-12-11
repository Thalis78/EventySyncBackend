package com.br.eventsync.repositories;

import com.br.eventsync.entities.Inscricao;
import com.br.eventsync.entities.constantes.StatusInscricao;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {

    Optional<Inscricao> findById(Integer id);


    Optional<Inscricao> findByEventoIdAndUsuarioId(Integer eventoId, Integer usuarioId);

}