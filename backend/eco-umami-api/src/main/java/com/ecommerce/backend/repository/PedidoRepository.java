package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}