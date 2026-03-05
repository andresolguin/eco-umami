package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Usuario;
import com.ecommerce.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id){
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Integer id){
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> buscarPorNombre(String nombre){
        return usuarioRepository.findByNombre(nombre);
    }

    public List<Usuario> buscarActivos(){
        return usuarioRepository.findByEstadoTrue();
    }

    public Optional<Usuario> buscarPorMail(String mail){
        return usuarioRepository.findByMail(mail);
    }

    public Optional<Usuario> buscarPorCuit(String cuit){
        return usuarioRepository.findByCuit(cuit);
    }

    public Optional<Usuario> buscarPorDni(String dni){
        return usuarioRepository.findByDni(dni);
    }

}