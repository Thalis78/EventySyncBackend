package com.br.eventsync.services;

import com.br.eventsync.dtos.response.InscricaoResponseDTO;
import com.br.eventsync.entities.Evento;
import com.br.eventsync.entities.Inscricao;
import com.br.eventsync.entities.Usuario;
import com.br.eventsync.entities.constantes.StatusInscricao;
import com.br.eventsync.entities.constantes.TipoEvento;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.repositories.EventoRepository;
import com.br.eventsync.repositories.InscricaoRepository;
import com.br.eventsync.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Inscricao aprovarInscricao(Integer id) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new DefaultApiException("Inscrição não encontrada"));

        if (inscricao.getStatusInscricao() == StatusInscricao.APROVADA) {
            throw new DefaultApiException("Inscrição já aprovada");
        }

        inscricao.setStatusInscricao(StatusInscricao.APROVADA);
        return inscricaoRepository.save(inscricao);
    }

    public Inscricao recusarInscricao(Integer id) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new DefaultApiException("Inscrição não encontrada"));

        if (inscricao.getStatusInscricao() == StatusInscricao.CANCELADA) {
            throw new DefaultApiException("Inscrição já recusada");
        }

        inscricao.setStatusInscricao(StatusInscricao.CANCELADA);
        return inscricaoRepository.save(inscricao);
    }

    public Inscricao confirmarPagamento(Integer id) {
        Inscricao inscricao = inscricaoRepository.findById(id)
                .orElseThrow(() -> new DefaultApiException("Inscrição não encontrada"));

        if (inscricao.getStatusInscricao() != StatusInscricao.AGUARDANDO_PAGAMENTO) {
            throw new DefaultApiException("Apenas inscrições aguardando pagamento podem ter o pagamento confirmado");
        }

        inscricao.setDataHoraPagamento(LocalDateTime.now());

        inscricao.setStatusInscricao(StatusInscricao.APROVADA);

        return inscricaoRepository.save(inscricao);
    }

    public Evento fecharInscricoes(Integer eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new DefaultApiException("Evento não encontrado"));

        if (evento.isInscricaoAberta()) {
            evento.setInscricaoAberta(false);
            evento.setInscricaoFecha(LocalDateTime.now());
            return eventoRepository.save(evento);
        } else {
            throw new DefaultApiException("As inscrições já estão fechadas para este evento.");
        }
    }

    public Evento abrirInscricoes(Integer eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new DefaultApiException("Evento não encontrado"));

        if (!evento.isInscricaoAberta()) {
            evento.setInscricaoAberta(true);
            evento.setInscricaoAbre(LocalDateTime.now());
            return eventoRepository.save(evento);
        } else {
            throw new DefaultApiException("As inscrições já estão abertas para este evento.");
        }
    }

    public Inscricao solicitarInscricao(Integer eventoId, Integer usuarioId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new DefaultApiException("Evento não encontrado"));

        if (!evento.isInscricaoAberta()) {
            throw new DefaultApiException("Inscrições não estão abertas para este evento.");
        }

        long numInscricoes = evento.getInscricoes().stream()
                .filter(inscricao -> inscricao.getStatusInscricao() == StatusInscricao.APROVADA)
                .count();

        if (numInscricoes >= evento.getMaxInscricao()) {
            throw new DefaultApiException("Número máximo de inscrições atingido.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new DefaultApiException("Usuário não encontrado"));

        Inscricao inscricao = new Inscricao();
        inscricao.setEvento(evento);
        inscricao.setUsuario(usuario);
        inscricao.setDataHoraInscricao(LocalDateTime.now());
        inscricao.setCertificadoEmitido(false);

        if (evento.getTipoEvento() == TipoEvento.GRATUITO) {
            inscricao.setStatusInscricao(StatusInscricao.PENDENTE);
        } else if (evento.getTipoEvento() == TipoEvento.PAGO) {
            inscricao.setStatusInscricao(StatusInscricao.AGUARDANDO_PAGAMENTO);
        }

        return inscricaoRepository.save(inscricao);
    }


    public List<InscricaoResponseDTO> listarInscricoes(Integer eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new DefaultApiException("Evento não encontrado"));

        return evento.getInscricoes().stream()
                .map(inscricao -> new InscricaoResponseDTO(
                        inscricao.getId(),
                        inscricao.isCertificadoEmitido(),
                        inscricao.getStatusInscricao(),
                        inscricao.getDataHoraInscricao().toString(),
                        inscricao.getUsuario().getNome()
                ))
                .collect(Collectors.toList());
    }



}
