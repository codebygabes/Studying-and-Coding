package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;
import util.Conexao;

public class UsuarioDAO {

    // CREATE
    public void inserir(Usuario usuario) {

        String sql = "INSERT INTO usuario (login, senha)\nVALUES (?, ?)\n";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Usuário cadastrado!");

        } catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

    // Busca usuário pelo login para autenticação
    public Usuario buscarPorLogin(String login) {

        String sql =
                "SELECT * FROM usuario WHERE login = ?";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, login);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setLogin(
                        rs.getString("login"));

                usuario.setSenha(
                        rs.getString("senha"));

                rs.close();
                stmt.close();
                conn.close();

                return usuario;

            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

        return null;

    }

}
