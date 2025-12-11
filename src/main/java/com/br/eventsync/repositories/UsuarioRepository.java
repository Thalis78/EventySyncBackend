package com.br.eventsync.repositories;

import com.br.eventsync.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findById(Integer id);


    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}