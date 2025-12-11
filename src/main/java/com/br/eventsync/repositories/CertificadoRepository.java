package com.br.eventsync.repositories;

import com.br.eventsync.entities.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificadoRepository extends JpaRepository<Certificado, Integer> {

    boolean existsByEventoIdAndParticipanteId(Integer eventoId, Integer participanteId);

    Optional<Certificado> findByEventoIdAndParticipanteId(Integer eventoId, Integer usuarioId);

}
