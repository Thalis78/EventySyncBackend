package com.br.eventsync.repositories;

import com.br.eventsync.entities.Amizade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmizadeRepository extends JpaRepository<Amizade,Integer> {
    Optional<Amizade> findById(Integer id);

    @Query("SELECT a FROM Amizade a WHERE " +
            "((a.solicitante.id = :id1 AND a.destinatario.id = :id2) OR " +
            "(a.solicitante.id = :id2 AND a.destinatario.id = :id1))")
    Optional<Amizade> buscarRelacionamentoEntreUsuarios(@Param("id1") Integer id1, @Param("id2") Integer id2);
}
