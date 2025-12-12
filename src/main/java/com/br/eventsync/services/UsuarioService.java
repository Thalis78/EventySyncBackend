package com.br.eventsync.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.br.eventsync.entities.Usuario;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario obterPerfil(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token de autenticação inválido ou ausente.");
        }

        String jwtToken = token.substring(7);

        try {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            String email = decodedJWT.getSubject();

            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter informações do usuário a partir do token: " + e.getMessage());
        }
    }



    public Usuario atualizarPerfil(Usuario usuarioAtualizado, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token de autenticação inválido ou ausente.");
        }

        String jwtToken = token.substring(7);

        try {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            String email = decodedJWT.getSubject();

            Usuario usuarioExistente = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new DefaultApiException("Usuário não encontrado."));

            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setCidade(usuarioAtualizado.getCidade());
            usuarioExistente.setVisibilidadeParticipacao(usuarioAtualizado.isVisibilidadeParticipacao());
            usuarioExistente.setPapelUsuario(usuarioAtualizado.getPapelUsuario());

            return usuarioRepository.save(usuarioExistente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o perfil do usuário a partir do token: " + e.getMessage());
        }
    }

}
