package com.br.eventsync.controllers;

import com.br.eventsync.entities.Usuario;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.services.UsuarioService;
import com.br.eventsync.dtos.response.MessageResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<?> obterPerfil(HttpServletRequest request) {
        try {
            Usuario usuario = usuarioService.obterPerfil(request);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/me")
    public ResponseEntity<?> atualizarPerfil(@RequestBody Usuario usuarioAtualizado, HttpServletRequest request) {
        try {
            Usuario usuario = usuarioService.atualizarPerfil(usuarioAtualizado, request);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
