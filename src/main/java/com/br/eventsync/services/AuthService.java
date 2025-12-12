package com.br.eventsync.services;

import com.br.eventsync.dtos.request.RegisterRequestDTO;
import com.br.eventsync.entities.Usuario;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.repositories.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String SECRET = "a1f9c3d4e5b67890c2f3a4b5d6e7f8091a2b3c4d5e6f7081920a1b2c3d4e5f60";

    public void register(RegisterRequestDTO registerRequestDTO) {
        if (usuarioRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new DefaultApiException("E-mail já registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(registerRequestDTO.getNome());
        usuario.setEmail(registerRequestDTO.getEmail());
        usuario.setHashSenha(registerRequestDTO.getSenha());
        usuario.setPapelUsuario(registerRequestDTO.getPapelUsuario());
        usuario.setCidade(registerRequestDTO.getCidade());

        usuarioRepository.save(usuario);
    }

    public String login(String senha, String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new DefaultApiException("Usuário não encontrado"));

        if (!usuario.getHashSenha().equals(senha)) {
            throw new DefaultApiException("Credenciais inválidas");
        }

        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + 24 * 60 * 60 * 1000L);

        return JWT.create()
                .withSubject(usuario.getEmail())
                .withClaim("papelUsuario", usuario.getPapelUsuario().name())
                .withIssuedAt(agora)
                .withExpiresAt(expiracao)
                .sign(Algorithm.HMAC256(SECRET));
    }
}
