package com.ecommerce.backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String mail;
    private String pass;
    private Integer idTipoUsuario;
    private Integer idTipoPersona;
}