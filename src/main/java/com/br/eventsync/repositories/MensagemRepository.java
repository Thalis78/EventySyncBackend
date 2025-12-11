package com.br.eventsync.repositories;

import com.br.eventsync.entities.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
    List<Mensagem> findByRemetenteIdOrDestinatarioId(Integer remetenteId, Integer destinatarioId);
}
