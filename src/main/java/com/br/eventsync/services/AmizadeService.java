package com.br.eventsync.services;

import com.br.eventsync.dtos.request.AmizadeRequestDTO;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.entities.Amizade;
import com.br.eventsync.entities.Usuario;
import com.br.eventsync.entities.constantes.StatusAmizade;
import com.br.eventsync.entities.constantes.StatusInscricao;
import com.br.eventsync.repositories.AmizadeRepository;
import com.br.eventsync.repositories.InscricaoRepository;
import com.br.eventsync.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Transactional
    public Amizade solicitarAmizade(AmizadeRequestDTO amizadeDTO) {
        Integer solicitanteId = amizadeDTO.getSolicitanteId();
        Integer destinatarioId = amizadeDTO.getDestinatarioId();
        Integer eventoId = amizadeDTO.getEventoId();

        Usuario solicitante = usuarioRepository.findById(solicitanteId)
                .orElseThrow(() -> new DefaultApiException("Solicitante não encontrado."));
        Usuario destinatario = usuarioRepository.findById(destinatarioId)
                .orElseThrow(() -> new DefaultApiException("Destinatário não encontrado."));

        if (solicitanteId.equals(destinatarioId)) {
            throw new DefaultApiException("Você não pode solicitar amizade para si mesmo.");
        }

        boolean inscricaoOk = inscricaoRepository.findByEventoIdAndUsuarioId(eventoId, solicitanteId)
                .filter(i -> i.getStatusInscricao() == StatusInscricao.APROVADA)
                .isPresent() && inscricaoRepository.findByEventoIdAndUsuarioId(eventoId, destinatarioId)
                .filter(i -> i.getStatusInscricao() == StatusInscricao.APROVADA)
                .isPresent();

        if (!inscricaoOk) {
            throw new DefaultApiException("Ambos devem estar com status 'APROVADO' no Evento " + eventoId);
        }

        amizadeRepository.buscarRelacionamentoEntreUsuarios(solicitanteId, destinatarioId)
                .ifPresent(a -> {
                    throw new DefaultApiException("Relacionamento já existe ou está pendente.");
                });

        Amizade amizade = new Amizade();
        amizade.setSolicitante(solicitante);
        amizade.setDestinatario(destinatario);
        amizade.setStatusAmizade(StatusAmizade.PENDENTE);

        return amizadeRepository.save(amizade);
    }

    @Transactional
    public Amizade aceitarAmizade(Integer amizadeId) {
        return alterarStatusAmizade(amizadeId, StatusAmizade.ACEITA);
    }

    @Transactional
    public Amizade recusarAmizade(Integer amizadeId) {
        return alterarStatusAmizade(amizadeId, StatusAmizade.RECUSADA);
    }

    @Transactional
    public void desfazerAmizade(Integer amizadeId) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
                .orElseThrow(() -> new DefaultApiException("Amizade não encontrada."));
        amizadeRepository.delete(amizade);
    }

    private Amizade alterarStatusAmizade(Integer amizadeId, StatusAmizade status) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
                .orElseThrow(() -> new DefaultApiException("Pedido de amizade não encontrado."));

        if (!amizade.getDestinatario().getId().equals(amizade.getSolicitante().getId())) {
            throw new DefaultApiException("Ação não autorizada. Somente o destinatário pode aceitar ou recusar a amizade.");
        }

        if (amizade.getStatusAmizade() != StatusAmizade.PENDENTE) {
            throw new DefaultApiException("O pedido já foi aceito ou recusado.");
        }

        amizade.setStatusAmizade(status);
        return amizadeRepository.save(amizade);
    }

}
