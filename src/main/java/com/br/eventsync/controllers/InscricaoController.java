package com.br.eventsync.controllers;

import com.br.eventsync.dtos.response.MessageResponseDTO;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.services.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<?> aprovarInscricao(@PathVariable Integer id) {
        try {
            inscricaoService.aprovarInscricao(id);
            return new ResponseEntity<>(new MessageResponseDTO("Inscrição aprovada com sucesso."), HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/recusar")
    public ResponseEntity<?> recusarInscricao(@PathVariable Integer id) {
        try {
            inscricaoService.recusarInscricao(id);
            return new ResponseEntity<>(new MessageResponseDTO("Inscrição recusada com sucesso."), HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/confirmar-pagamento")
    public ResponseEntity<?> confirmarPagamento(@PathVariable Integer id) {
        try {
            inscricaoService.confirmarPagamento(id);
            return new ResponseEntity<>(new MessageResponseDTO("Pagamento confirmado com sucesso."), HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
