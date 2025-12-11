package com.br.eventsync.services;

import com.br.eventsync.entities.Certificado;
import com.br.eventsync.entities.Evento;
import com.br.eventsync.entities.Usuario;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.repositories.CertificadoRepository;
import com.br.eventsync.repositories.EventoRepository;
import com.br.eventsync.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CertificadoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CertificadoRepository certificadoRepository;


    public Certificado gerarCertificado(Integer eventoId, Integer usuarioId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new DefaultApiException("Evento não encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new DefaultApiException("Usuário não encontrado"));

        if (certificadoRepository.existsByEventoIdAndParticipanteId(eventoId, usuarioId)) {
            throw new DefaultApiException("Usuário já possui certificado para este evento");
        }

        Certificado certificado = new Certificado();
        certificado.setEvento(evento);
        certificado.setParticipante(usuario);
        certificado.setDataHoraEmissao(LocalDateTime.now());

        return certificadoRepository.save(certificado);
    }

    public Certificado visualizarCertificado(Integer eventoId, Integer usuarioId) {
        Certificado certificado = certificadoRepository.findByEventoIdAndParticipanteId(eventoId, usuarioId)
                .orElseThrow(() -> new DefaultApiException("Certificado não encontrado"));

        return certificado;
    }
}
