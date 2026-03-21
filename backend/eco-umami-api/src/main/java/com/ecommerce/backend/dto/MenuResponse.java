package com.ecommerce.backend.dto;

import java.util.List;

public class MenuResponse {

    private String rol;
    private List<String> items;

    public MenuResponse() {
    }

    public MenuResponse(String rol, List<String> items) {
        this.rol = rol;
        this.items = items;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}