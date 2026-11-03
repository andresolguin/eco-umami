package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.CarritoItem;
import com.ecommerce.backend.entity.CarritoItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, CarritoItemId> {

    List<CarritoItem> findByCarritoId(Integer idCarrito);
}