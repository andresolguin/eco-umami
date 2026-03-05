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

    @GetMapping
    public List<Usuario> listar(){
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> buscarPorId(@PathVariable Integer id){
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario){
        return usuarioService.guardar(usuario);
    }

    @PutMapping("/{id}")
    public Usuario modificar(@PathVariable Integer id, @RequestBody Usuario usuario){
        usuario.setId(id);
        return usuarioService.guardar(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        usuarioService.eliminar(id);
    }

    @GetMapping("/nombre/{nombre}")
    public List<Usuario> buscarPorNombre(@PathVariable String nombre){
        return usuarioService.buscarPorNombre(nombre);
    }

    @GetMapping("/activos")
    public List<Usuario> activos(){
        return usuarioService.buscarActivos();
    }

    @GetMapping("/mail/{mail}")
    public Optional<Usuario> buscarPorMail(@PathVariable String mail){
        return usuarioService.buscarPorMail(mail);
    }

    @GetMapping("/cuit/{cuit}")
    public Optional<Usuario> buscarPorCuit(@PathVariable String cuit){
        return usuarioService.buscarPorCuit(cuit);
    }

    @GetMapping("/dni/{dni}")
    public Optional<Usuario> buscarPorDni(@PathVariable String dni){
        return usuarioService.buscarPorDni(dni);
    }
}