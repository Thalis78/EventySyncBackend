package com.br.eventsync.controllers;

import com.br.eventsync.dtos.request.EventoRequestDTO;
import com.br.eventsync.dtos.response.InscricaoEventoResponseDTO;
import com.br.eventsync.dtos.response.InscricaoResponseDTO;
import com.br.eventsync.dtos.response.MessageResponseDTO;
import com.br.eventsync.entities.Certificado;
import com.br.eventsync.entities.Evento;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.services.CertificadoService;
import com.br.eventsync.services.EventoService;
import com.br.eventsync.services.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private InscricaoService inscricaoService;

    @Autowired
    private CertificadoService certificadoService;

    //OK
    @PostMapping
    public ResponseEntity<?> criarEvento(@RequestBody EventoRequestDTO evento) {
        try {
            Evento novoEvento = eventoService.criarEvento(evento);
            return new ResponseEntity<>(novoEvento, HttpStatus.CREATED);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //OK
    @PutMapping("/{id}")
    public ResponseEntity<?> editarEvento(@PathVariable Integer id, @RequestBody EventoRequestDTO evento) {
        try {
            Evento eventoAtualizado = eventoService.editarEvento(id, evento);
            return new ResponseEntity<>(eventoAtualizado, HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //OK
    @GetMapping("/{id}")
    public ResponseEntity<?> obterDetalhesEvento(@PathVariable Integer id) {
        try {
            Evento evento = eventoService.obterDetalhesEvento(id);
            return new ResponseEntity<>(evento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    //OK
    @PostMapping("/{id}/abrir-inscricoes")
    public ResponseEntity<?> abrirInscricoes(@PathVariable Integer id) {
        try {
            inscricaoService.abrirInscricoes(id);
            return new ResponseEntity<>(new MessageResponseDTO("Inscrições abertas com sucesso."), HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //OK
    @PostMapping("/{id}/fechar-inscricoes")
    public ResponseEntity<?> fecharInscricoes(@PathVariable Integer id) {
        try {
            inscricaoService.fecharInscricoes(id);
            return new ResponseEntity<>(new MessageResponseDTO("Inscrições fechadas com sucesso."), HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // OK
    @PostMapping("/{id}/registrar")
    public ResponseEntity<?> solicitarInscricao(@PathVariable Integer id, @RequestBody Integer usuarioId) {
        try {
            inscricaoService.solicitarInscricao(id, usuarioId);
            return new ResponseEntity<>(new MessageResponseDTO("Inscrição realizada com sucesso."), HttpStatus.CREATED);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    // OK
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> listarEventosDoUsuario(@PathVariable Integer usuarioId) {
        try {
            List<InscricaoEventoResponseDTO> eventosDoUsuario = eventoService.listarEventosPorUsuario(usuarioId);
            return new ResponseEntity<>(eventosDoUsuario, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    //OK
    @GetMapping("/pagos")
    public ResponseEntity<?> listarEventosPagos() {
        try {
            List<Evento> eventosPagos = eventoService.listarEventosPagos();
            return new ResponseEntity<>(eventosPagos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    //OK
    @GetMapping("/gratuitos")
    public ResponseEntity<?> listarEventosGratuitos() {
        try {
            List<Evento> eventosGratuitos = eventoService.listarEventosGratuitos();
            return new ResponseEntity<>(eventosGratuitos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    //OK
    @GetMapping("/organizador/{organizadorId}")
    public ResponseEntity<?> listarEventosDoOrganizador(@PathVariable Integer organizadorId) {
        try {
            List<Evento> eventosDoOrganizador = eventoService.listarEventosDoOrganizador(organizadorId);
            return new ResponseEntity<>(eventosDoOrganizador, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    //OK
    @GetMapping("/{id}/inscricoes")
    public ResponseEntity<?> listarInscricoes(@PathVariable Integer id) {
        try {
            List<InscricaoResponseDTO> inscricoes = inscricaoService.listarInscricoes(id);
            return new ResponseEntity<>(inscricoes, HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{eventoId}/usuario/{usuarioId}")
    public ResponseEntity<?> visualizarCertificado(@PathVariable Integer eventoId, @PathVariable Integer usuarioId) {
        try {
            Certificado certificado = certificadoService.visualizarCertificado(eventoId, usuarioId);
            return new ResponseEntity<>(certificado, HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{eventoId}/certificados/{usuarioId}")
    public ResponseEntity<?> gerarCertificado(@PathVariable Integer eventoId, @PathVariable Integer usuarioId) {
        try {
            Certificado certificado = certificadoService.gerarCertificado(eventoId, usuarioId);
            return new ResponseEntity<>(certificado, HttpStatus.CREATED);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
