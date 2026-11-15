package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByUsuarioIdOrderByFechaPedidoDesc(Integer idUsuario);

    List<Pedido> findByVendedorIdOrderByFechaPedidoDesc(Integer idVendedor);
}