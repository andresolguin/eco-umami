package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.Usuario;
import com.ecommerce.backend.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // 🔹 LISTAR TODOS (activos + inactivos)
    @GetMapping
    public List<Usuario> listarTodos(){
        return usuarioService.listarTodos();
    }

    // 🔹 LISTAR SOLO ACTIVOS
    @GetMapping("/activos")
    public List<Usuario> listarActivos(){
        return usuarioService.buscarActivos();
    }

    // 🔹 LISTAR POR ESTADO (true / false)
    @GetMapping("/estado")
    public List<Usuario> listarPorEstado(@RequestParam Boolean estado){
        return usuarioService.listarPorEstado(estado);
    }

    // 🔹 BUSCAR POR ID
    @GetMapping("/{id}")
    public Optional<Usuario> buscarPorId(@PathVariable Integer id){
        return usuarioService.buscarPorId(id);
    }

    // 🔹 CREAR
    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario){
        return usuarioService.guardar(usuario);
    }

    // 🔹 ELIMINACIÓN LÓGICA
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        usuarioService.eliminar(id);
    }

    // 🔹 REACTIVAR
    @PutMapping("/reactivar/{id}")
    public void reactivar(@PathVariable Integer id){
        usuarioService.reactivar(id);
    }

    // 🔹 BUSCAR POR NOMBRE
    @GetMapping("/buscar")
    public List<Usuario> buscarPorNombre(@RequestParam String nombre){
        return usuarioService.buscarPorNombre(nombre);
    }

    // 🔹 BUSCAR POR MAIL
    @GetMapping("/mail")
    public Optional<Usuario> buscarPorMail(@RequestParam String mail){
        return usuarioService.buscarPorMail(mail);
    }

    // 🔹 BUSCAR POR CUIT
    @GetMapping("/cuit")
    public Optional<Usuario> buscarPorCuit(@RequestParam String cuit){
        return usuarioService.buscarPorCuit(cuit);
    }

    // 🔹 BUSCAR POR DNI
    @GetMapping("/dni")
    public Optional<Usuario> buscarPorDni(@RequestParam String dni){
        return usuarioService.buscarPorDni(dni);
    }
}