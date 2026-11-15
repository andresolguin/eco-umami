package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.PedidoItem;
import com.ecommerce.backend.entity.PedidoItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoItemRepository
        extends JpaRepository<PedidoItem, PedidoItemId> {

    List<PedidoItem> findByPedidoId(Integer idPedido);
}