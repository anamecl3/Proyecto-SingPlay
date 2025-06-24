package com.SingPlayProyect.Controller;

import com.SingPlayProyect.DTO.UsuarioDTO;
import com.SingPlayProyect.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // GET: http://localhost:8065/api/usuario
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodosLosUsuarios() {
        return service.listarUsuarios();
    }

    // POST: http://localhost:8065/api/usuario
    @PostMapping
    public ResponseEntity<Map<String, Object>> agregarUsuario(@RequestBody UsuarioDTO usuario) {
        return service.agregarUsuario(usuario);
    }
}