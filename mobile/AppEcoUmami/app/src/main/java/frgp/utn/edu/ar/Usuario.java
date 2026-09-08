package frgp.utn.edu.ar;

public class Usuario {

    private Integer id;
    private String mail;
    private String pass;
    private TipoUsuario tipoUsuario;
    private TipoPersona tipoPersona;
    private Boolean estado;

    public Integer getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public Boolean getEstado() {
        return estado;
    }

    public static class TipoUsuario {
        private Integer id;
        private String descripcion;

        public Integer getId() {
            return id;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    public static class TipoPersona {
        private Integer id;
        private String descripcion;

        public Integer getId() {
            return id;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
}