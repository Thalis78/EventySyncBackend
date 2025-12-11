package com.br.eventsync.repositories;

import com.br.eventsync.entities.Evento;
import com.br.eventsync.entities.Usuario;
import com.br.eventsync.entities.constantes.StatusEvento;
import com.br.eventsync.entities.constantes.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    Optional<Evento> findById(Integer id);

    List<Evento> findByTipoEvento(TipoEvento tipoEvento);

    List<Evento> findByOrganizador(Usuario organizador);
}