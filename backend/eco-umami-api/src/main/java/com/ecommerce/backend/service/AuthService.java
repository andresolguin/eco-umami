package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.LoginRequest;
import com.ecommerce.backend.dto.RegisterRequest;
import com.ecommerce.backend.entity.TipoPersona;
import com.ecommerce.backend.entity.TipoUsuario;
import com.ecommerce.backend.entity.Usuario;
import com.ecommerce.backend.repository.TipoPersonaRepository;
import com.ecommerce.backend.repository.TipoUsuarioRepository;
import com.ecommerce.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;
    private final TipoPersonaRepository tipoPersonaRepository;

    public Usuario register(RegisterRequest req) {
        if (usuarioRepository.existsByMail(req.getMail())) {
            throw new IllegalArgumentException("El mail ya está registrado");
        }

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(req.getIdTipoUsuario())
                .orElseThrow(() -> new IllegalArgumentException("TipoUsuario inválido"));

        TipoPersona tipoPersona = null;
        if (req.getIdTipoPersona() != null) {
            tipoPersona = tipoPersonaRepository.findById(req.getIdTipoPersona())
                    .orElseThrow(() -> new IllegalArgumentException("TipoPersona inválido"));
        }

        if (req.getIdTipoUsuario() == 1) {
            throw new IllegalArgumentException("No se puede registrar un administrador");
        }

        if (req.getIdTipoUsuario() == 2 && req.getIdTipoPersona() != 1) {
            throw new IllegalArgumentException("Un cliente debe ser persona física");
        }

        if (req.getIdTipoUsuario() == 3 &&
                req.getIdTipoPersona() != 1 &&
                req.getIdTipoPersona() != 2) {
            throw new IllegalArgumentException("Tipo de persona inválido para comercio");
        }

        Usuario u = Usuario.builder()
                .mail(req.getMail())
                .pass(req.getPass())
                .tipoUsuario(tipoUsuario)
                .tipoPersona(tipoPersona)
                .estado(true)
                .build();

        return usuarioRepository.save(u);
    }

    public Usuario login(LoginRequest req) {
        Usuario u = usuarioRepository.findByMail(req.getMail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!Boolean.TRUE.equals(u.getEstado())) {
            throw new IllegalArgumentException("Usuario inactivo");
        }

        if (!u.getPass().equals(req.getPass())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        return u;
    }
}