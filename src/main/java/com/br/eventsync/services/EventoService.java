package com.br.eventsync.services;

import com.br.eventsync.dtos.request.EventoRequestDTO;
import com.br.eventsync.entities.*;
import com.br.eventsync.entities.constantes.PapelUsuario;
import com.br.eventsync.entities.constantes.TipoEvento;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    private void atualizarEventoComDTO(Evento evento, EventoRequestDTO eventoRequestDTO) {
        evento.setTitulo(eventoRequestDTO.getTitulo());
        evento.setDescricao(eventoRequestDTO.getDescricao());
        evento.setLocalEndereco(eventoRequestDTO.getLocalEndereco());
        evento.setDataHoraInicio(eventoRequestDTO.getDataHoraInicio());
        evento.setDataHoraFinal(eventoRequestDTO.getDataHoraFinal());
        evento.setPreco(eventoRequestDTO.getPreco());
        evento.setTipoEvento(eventoRequestDTO.getTipoEvento());
        evento.setInscricaoAbre(eventoRequestDTO.getInscricaoAbre());
        evento.setInscricaoFecha(eventoRequestDTO.getInscricaoFecha());
        evento.setMaxInscricao(eventoRequestDTO.getMaxInscricao());
        evento.setStatusEvento(eventoRequestDTO.getStatusEvento());
        evento.setCargaHoraria(eventoRequestDTO.getCargaHoraria());
        evento.setInscricaoAberta(eventoRequestDTO.isInscricaoAberta());
    }


    public Evento criarEvento(EventoRequestDTO eventoRequestDTO) {
        Usuario usuario = usuarioRepository.findById(eventoRequestDTO.getOrganizadorId())
                .orElseThrow(() -> new DefaultApiException("Organizador não encontrado"));

        if (usuario.getPapelUsuario() != PapelUsuario.ORGANIZADOR) {
            throw new DefaultApiException("Somente usuários com papel de ORGANIZADOR podem criar eventos.");
        }

        Evento evento = new Evento();
        evento.setOrganizador(usuario);

        atualizarEventoComDTO(evento, eventoRequestDTO);

        return eventoRepository.save(evento);
    }

    public Evento editarEvento(Integer id, EventoRequestDTO eventoRequestDTO) {
        Evento eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new DefaultApiException("Evento não encontrado"));

        Usuario usuario = usuarioRepository.findById(eventoRequestDTO.getOrganizadorId())
                .orElseThrow(() -> new DefaultApiException("Organizador não encontrado"));

        if (usuario.getPapelUsuario() != PapelUsuario.ORGANIZADOR) {
            throw new DefaultApiException("Somente usuários com papel de ORGANIZADOR podem editar eventos.");
        }

        eventoExistente.setOrganizador(usuario);
        atualizarEventoComDTO(eventoExistente, eventoRequestDTO);

        return eventoRepository.save(eventoExistente);
    }

    public List<Evento> listarEventosDoOrganizador(Integer organizadorId) {
        Usuario usuario = usuarioRepository.findById(organizadorId)
                .orElseThrow(() -> new DefaultApiException("Organizador não encontrado"));

        if (usuario.getPapelUsuario() != PapelUsuario.ORGANIZADOR) {
            throw new DefaultApiException("Somente usuários com papel de ORGANIZADOR podem listar eventos.");
        }

        return eventoRepository.findByOrganizador(usuario);
    }

    public List<Evento> listarEventosPagos() {
        return eventoRepository.findByTipoEvento(TipoEvento.PAGO);
    }

    public List<Evento> listarEventosGratuitos() {
        return eventoRepository.findByTipoEvento(TipoEvento.GRATUITO);
    }


    public Evento obterDetalhesEvento(Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new DefaultApiException("Evento não encontrado"));
    }
}
