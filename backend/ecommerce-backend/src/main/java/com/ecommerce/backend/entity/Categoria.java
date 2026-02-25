package com.ecommerce.backend.entity;

public class Categoria {

    private int idCategoria;
    private String descripcionCat;
    private boolean estado;

    // getters y setters

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcionCat() {
        return descripcionCat;
    }

    public void setDescripcionCat(String descripcionCat) {
        this.descripcionCat = descripcionCat;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
