package com.br.eventsync.controllers;

import com.br.eventsync.dtos.request.LoginRequestDTO;
import com.br.eventsync.dtos.request.RegisterRequestDTO;
import com.br.eventsync.dtos.response.AuthResponseDTO;
import com.br.eventsync.dtos.response.MessageResponseDTO;
import com.br.eventsync.exception.DefaultApiException;
import com.br.eventsync.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    //OK
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequestDTO registerRequestDTO) {
        try {
            authService.register(registerRequestDTO);
            return new ResponseEntity<>(registerRequestDTO, HttpStatus.CREATED);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> entrar(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            AuthResponseDTO responseDTO = authService.login(loginRequestDTO.getSenha(), loginRequestDTO.getEmail());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (DefaultApiException e) {
            return new ResponseEntity<>(new MessageResponseDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
