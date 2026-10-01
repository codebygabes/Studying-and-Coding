package model;

import interfaces.Autenticavel;

public class Usuario implements Autenticavel {

    private String login;
    private String senha;

    @Override
    public boolean autenticar(String login, String senha) {

        return this.login.equals(login)
                && this.senha.equals(senha);

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}