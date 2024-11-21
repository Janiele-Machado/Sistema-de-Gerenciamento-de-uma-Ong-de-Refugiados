package ong;

public class Voluntarios extends Usuarios {

    private String email;
    private String habilidades;

    public Voluntarios(String nome, String data_nasc, String nacionalidade, String email, String habilidades) {
        super(nome, data_nasc, nacionalidade);
        this.email = email;
        this.habilidades = habilidades;

    }

}
