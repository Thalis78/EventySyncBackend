package com.br.eventsync.services;

import com.br.eventsync.entities.Avaliacao;
import com.br.eventsync.entities.Evento;
import com.br.eventsync.entities.Usuario;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.repositories.AvaliacaoRepository;
import com.br.eventsync.repositories.EventoRepository;
import com.br.eventsync.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AvaliacaoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public Avaliacao criarAvaliacao(Integer eventoId, Integer usuarioId, int nota, String comentario) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new DefaultApiException("Evento não encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new DefaultApiException("Usuário não encontrado"));

        if (avaliacaoRepository.findByEventoIdAndUsuarioId(eventoId, usuarioId).isPresent()) {
            throw new DefaultApiException("Usuário já avaliou este evento");
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setEvento(evento);
        avaliacao.setUsuario(usuario);
        avaliacao.setNota(nota);
        avaliacao.setComentario(comentario);
        avaliacao.setDataHoraAvaliacao(LocalDateTime.now());

        return avaliacaoRepository.save(avaliacao);
    }

}
