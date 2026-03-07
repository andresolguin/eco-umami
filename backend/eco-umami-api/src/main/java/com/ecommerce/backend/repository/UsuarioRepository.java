package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByNombre(String nombre);

    List<Usuario> findByEstadoTrue();

    Optional<Usuario> findByMail(String mail);

    Optional<Usuario> findByCuit(String cuit);

    Optional<Usuario> findByDni(String dni);

    boolean existsByMail(String mail);
}