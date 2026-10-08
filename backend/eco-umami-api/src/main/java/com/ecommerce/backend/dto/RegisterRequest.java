package com.ecommerce.backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String mail;
    private String pass;
    private Integer idTipoUsuario;
    private Integer idTipoPersona;

    public RegisterRequest(String mail, String pass,
                           Integer idTipoUsuario,
                           Integer idTipoPersona) {
        this.mail = mail;
        this.pass = pass;
        this.idTipoUsuario = idTipoUsuario;
        this.idTipoPersona = idTipoPersona;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public Integer getIdTipoPersona() {
        return idTipoPersona;
    }
}