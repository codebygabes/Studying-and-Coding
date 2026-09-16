package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import model.Funcionario;
import util.Conexao;

public class FuncionarioDAO {

    // CREATE
    public void inserir(Funcionario funcionario) {

        String sql = "INSERT INTO funcionario\n(nome, cargo, telefone)\nVALUES (?, ?, ?)\n";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getTelefone());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Funcionário cadastrado!");

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

    // READ
    public List<Funcionario> listar() {

        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = "SELECT * FROM funcionario";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Funcionario funcionario = new Funcionario();

                funcionario.setIdFuncionario(
                        rs.getInt("id_funcionario"));

                funcionario.setNome(
                        rs.getString("nome"));

                funcionario.setCargo(
                        rs.getString("cargo"));

                funcionario.setTelefone(
                        rs.getString("telefone"));

                funcionarios.add(funcionario);

            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

        return funcionarios;

    }

    // UPDATE
    public void atualizar(Funcionario funcionario) {

        String sql = "UPDATE funcionario\nSET nome = ?,\n    cargo = ?,\n    telefone = ?\nWHERE id_funcionario = ?\n";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getTelefone());

            stmt.setInt(4, funcionario.getIdFuncionario());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Funcionário atualizado!");

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

    // DELETE
    public void excluir(int idFuncionario) {

        String sql =
                "DELETE FROM funcionario WHERE id_funcionario = ?";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(1, idFuncionario);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Funcionário removido!");

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

}