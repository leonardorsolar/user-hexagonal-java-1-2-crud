package com.example.hexagonal.controller;

import com.example.hexagonal.dto.CreateUsuarioDTO;
import com.example.hexagonal.dto.UpdateUsuarioDTO;
import com.example.hexagonal.dto.UsuarioDTO;
import com.example.hexagonal.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Criar novo usuário
     * POST /api/usuarios
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody CreateUsuarioDTO createUsuarioDTO) {
        UsuarioDTO usuarioCriado = usuarioService.criar(createUsuarioDTO);

        URI location = URI.create("/api/usuarios/" + usuarioCriado.getId());

        return ResponseEntity.created(location).body(usuarioCriado);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API está funcionando!");
    }
}
