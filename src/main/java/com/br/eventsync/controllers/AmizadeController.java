package com.br.eventsync.controllers;

import com.br.eventsync.dtos.request.MensagemRequestDTO;
import com.br.eventsync.dtos.request.AmizadeRequestDTO;
import com.br.eventsync.dtos.response.MessageResponseDTO;
import com.br.eventsync.entities.Mensagem;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.services.AmizadeService;
import com.br.eventsync.services.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amigos")
public class AmizadeController {

    @Autowired
    private AmizadeService amizadeService;

    @Autowired
    private MensagemService mensagemService;

    @PostMapping("/solicitar-amizade")
    public ResponseEntity<?> solicitarAmizade(@RequestBody AmizadeRequestDTO amizadeDTO) {
        try {
            amizadeService.solicitarAmizade(amizadeDTO);
            return new ResponseEntity<>(amizadeDTO, HttpStatus.CREATED);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{amizadeId}/aceitar")
    public ResponseEntity<?> aceitarAmizade(@PathVariable Integer amizadeId) {
        try {
            amizadeService.aceitarAmizade(amizadeId);
            return new ResponseEntity<>(new MessageResponseDTO("Solicitação de amizade aceita com sucesso."), HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{amizadeId}/recusar")
    public ResponseEntity<?> recusarAmizade(@PathVariable Integer amizadeId) {
        try {
            amizadeService.recusarAmizade(amizadeId);
            return new ResponseEntity<>(new MessageResponseDTO("Solicitação de amizade recusada com sucesso."), HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{amizadeId}")
    public ResponseEntity<?> desfazerAmizade(@PathVariable Integer amizadeId) {
        try {
            amizadeService.desfazerAmizade(amizadeId);
            return new ResponseEntity<>(new MessageResponseDTO("Amizade desfeita com sucesso."), HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/mensagens")
    public ResponseEntity<?> enviarMensagem(@RequestBody MensagemRequestDTO mensagemRequestDTO) {
        try {
            mensagemService.enviarMensagem(mensagemRequestDTO.getRemetenteId(), mensagemRequestDTO.getDestinatarioId(), mensagemRequestDTO.getConteudo());
            return new ResponseEntity<>(mensagemRequestDTO, HttpStatus.CREATED);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/mensagens/{usuarioId}")
    public ResponseEntity<?> listarMensagens(@PathVariable Integer usuarioId) {
        try {
            List<Mensagem> mensagens = mensagemService.listarMensagens(usuarioId);
            return new ResponseEntity<>(mensagens, HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
