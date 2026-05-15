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

    // ✅ GUARDAR CON VALIDACIONES
    public Usuario guardar(Usuario usuario){

        // Verificar mail único
        if (usuarioRepository.existsByMail(usuario.getMail())) {
            throw new RuntimeException("El mail ya está registrado");
        }

        // Estado activo por defecto
        if (usuario.getEstado() == null) {
            usuario.setEstado(true);
        }

        return usuarioRepository.save(usuario);
    }

    // ✅ MODIFICAR SIN PISAR CONTRASEÑA
    public Usuario modificar(Integer id, Usuario usuario){
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar mail único si cambia
        if (!existente.getMail().equals(usuario.getMail()) &&
                usuarioRepository.existsByMail(usuario.getMail())) {
            throw new RuntimeException("El mail ya está en uso");
        }

        // Solo actualizar campos, NO la contraseña
        existente.setMail(usuario.getMail());
        existente.setNombre(usuario.getNombre());
        existente.setApellido(usuario.getApellido());
        existente.setDni(usuario.getDni());
        existente.setRazonSocial(usuario.getRazonSocial());
        existente.setCuit(usuario.getCuit());
        existente.setDireccion(usuario.getDireccion());
        existente.setCiudad(usuario.getCiudad());
        existente.setCodigoPostal(usuario.getCodigoPostal());
        existente.setTelefono(usuario.getTelefono());

        return usuarioRepository.save(existente);
    }

    // ✅ LOGIN CON VALIDACIONES
    public Usuario login(String mail, String pass){
        // Verificar que existe
        Usuario usuario = usuarioRepository.findByMail(mail)
                .orElseThrow(() -> new RuntimeException("Mail o contraseña incorrectos"));

        // Verificar que está activo
        if (!Boolean.TRUE.equals(usuario.getEstado())) {
            throw new RuntimeException("El usuario está inactivo");
        }

        // Verificar contraseña
        if (!usuario.getPass().equals(pass)) {
            throw new RuntimeException("Mail o contraseña incorrectos");
        }

        return usuario;
    }

    public void eliminar(Integer id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(false);
        usuarioRepository.save(usuario);
    }

    public void reactivar(Integer id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(true);
        usuarioRepository.save(usuario);
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

    public List<Usuario> listarPorEstado(Boolean estado){
        return usuarioRepository.findByEstado(estado);
    }
}