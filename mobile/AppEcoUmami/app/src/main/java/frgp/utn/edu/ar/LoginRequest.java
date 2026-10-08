package frgp.utn.edu.ar;

public class LoginRequest {

    private String mail;
    private String pass;

    public LoginRequest(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }
}