package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @Column(nullable = false, length = 100, unique = true)
    private String mail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String pass;

    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    @ManyToOne
    @JoinColumn(name = "id_tipo_persona")
    private TipoPersona tipoPersona;

    @Column(length = 20)
    private String dni;

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String apellido;

    @Column(length = 100)
    private String razonSocial;

    @Column(length = 20)
    private String cuit;

    @Column(length = 100)
    private String direccion;

    @Column(length = 50)
    private String ciudad;

    private Integer codigoPostal;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false)
    private Boolean estado;
}