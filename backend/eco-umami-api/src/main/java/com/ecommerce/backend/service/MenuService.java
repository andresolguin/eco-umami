package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.MenuResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    public MenuResponse obtenerMenuPorRol(String rol) {
        String rolNormalizado = rol.toUpperCase();

        switch (rolNormalizado) {
            case "CLIENTE":
                return new MenuResponse("CLIENTE", List.of(
                        "catalogo",
                        "carrito",
                        "mis_pedidos",
                        "mi_perfil"
                ));

            case "COMERCIO":
                return new MenuResponse("COMERCIO", List.of(
                        "mis_productos",
                        "publicar_producto",
                        "pedidos_recibidos",
                        "mi_perfil"
                ));

            case "ADMIN":
                return new MenuResponse("ADMIN", List.of(
                        "usuarios",
                        "comercios",
                        "reportes",
                        "configuracion"
                ));

            default:
                throw new IllegalArgumentException("Rol no válido: " + rol);
        }
    }
}