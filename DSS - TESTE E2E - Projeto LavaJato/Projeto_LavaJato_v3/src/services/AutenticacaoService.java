package services;

import dao.UsuarioDAO;
import model.Usuario;

public class AutenticacaoService {

    private UsuarioDAO dao = new UsuarioDAO();

    public boolean login(String login, String senha) {

        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O login é obrigatório.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "A senha é obrigatória.");
        }

        Usuario usuario = dao.buscarPorLogin(login);

        if (usuario == null) {
            return false;
        }

        // Usa o método autenticar() da interface Autenticavel
        return usuario.autenticar(login, senha);

    }

}
