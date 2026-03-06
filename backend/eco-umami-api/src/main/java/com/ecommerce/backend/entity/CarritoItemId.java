package com.ecommerce.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CarritoItemId implements Serializable {

    private Integer idCarrito;
    private Integer idProducto;
}