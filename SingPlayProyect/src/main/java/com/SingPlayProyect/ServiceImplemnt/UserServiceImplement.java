package com.SingPlayProyect.ServiceImplemnt;

import com.SingPlayProyect.DTO.UsuarioDTO;
import com.SingPlayProyect.Model.UsuarioModel;
import com.SingPlayProyect.Repository.UsuarioRepository;
import com.SingPlayProyect.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImplement implements UserDetailsService, UsuarioService {

    @Autowired
    private UsuarioRepository dao;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioModel usuario = dao.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con dicho email: "+email+ "no existe."));

        return new UserDetailImplement(usuario);
    }

    @Override
    public ResponseEntity<Map<String, Object>> listarUsuarios() {
        Map<String, Object> respuesta = new HashMap<>();
        List<UsuarioModel> usuarios = dao.findAll();

        if (!usuarios.isEmpty()) {
            respuesta.put("mensaje", "Lista de usuarios");
            respuesta.put("usuarios", usuarios);
            respuesta.put("status", HttpStatus.OK);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        } else {
            respuesta.put("mensaje", "No existen registros de usuarios");
            respuesta.put("status", HttpStatus.NOT_FOUND);
            respuesta.put("fecha", new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> agregarUsuario(UsuarioDTO dto) {
        Map<String, Object> respuesta = new HashMap<>();
        String passwordEncriptado = passwordEncoder.encode(dto.getClave());

        UsuarioModel usuario = new UsuarioModel();
        usuario.setPassword(passwordEncriptado);
        //usuario.setFechaRegistro(new Date());
        usuario.setUsuario(dto.getUsuario());
        usuario.setEmail(dto.getCorreo());
        usuario.setActivo(true);
        dao.save(usuario);
        respuesta.put("usuario", usuario);
        respuesta.put("mensaje", "Usuario añadido correctamente");
        respuesta.put("status", HttpStatus.CREATED);
        respuesta.put("fecha", new Date());
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}

