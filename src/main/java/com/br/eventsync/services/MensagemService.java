package com.br.eventsync.services;

import com.br.eventsync.entities.Mensagem;
import com.br.eventsync.entities.Usuario;
import com.br.eventsync.exception.DefaultApiException;

import com.br.eventsync.repositories.MensagemRepository;
import com.br.eventsync.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MensagemService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MensagemRepository mensagemRepository;

    @Transactional
    public void enviarMensagem(Integer remetenteId, Integer destinatarioId, String conteudo) {
        Usuario remetente = usuarioRepository.findById(remetenteId)
                .orElseThrow(() -> new DefaultApiException("Remetente não encontrado."));
        Usuario destinatario = usuarioRepository.findById(destinatarioId)
                .orElseThrow(() -> new DefaultApiException("Destinatário não encontrado."));

        Mensagem mensagem = new Mensagem();
        mensagem.setRemetente(remetente);
        mensagem.setDestinatario(destinatario);
        mensagem.setConteudo(conteudo);

        mensagemRepository.save(mensagem);
    }

    public List<Mensagem> listarMensagens(Integer usuarioId) {
        return mensagemRepository.findByRemetenteIdOrDestinatarioId(usuarioId, usuarioId);
    }
}
