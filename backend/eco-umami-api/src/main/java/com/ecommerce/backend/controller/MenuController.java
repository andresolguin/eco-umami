package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.MenuResponse;
import com.ecommerce.backend.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/api/menu")
    public MenuResponse obtenerMenu(@RequestParam String rol) {
        return menuService.obtenerMenuPorRol(rol);
    }
}