package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import model.Atendimento;
import model.Cliente;
import model.Funcionario;
import util.Conexao;

public class AtendimentoDAO {

    // CREATE
    public void inserir(Atendimento atendimento) {

        String sql = "INSERT INTO atendimento\n(id_cliente, id_funcionario,\n data_atendimento, observacao)\nVALUES (?, ?, ?, ?)\n";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(
                    1,
                    atendimento.getCliente()
                            .getIdCliente());

            stmt.setInt(
                    2,
                    atendimento.getFuncionario()
                            .getIdFuncionario());

            stmt.setTimestamp(
                    3,
                    Timestamp.valueOf(
                            atendimento.getDataAtendimento()));

            stmt.setString(
                    4,
                    atendimento.getObservacao());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Atendimento cadastrado!");

        } catch (SQLException e) {

            System.out.println(
                    "Erro: " + e.getMessage());

        }

    }

    // READ
    public List<Atendimento> listar() {

        List<Atendimento> atendimentos =
                new ArrayList<>();

        String sql =
                "SELECT * FROM atendimento";

        try {

            Connection conn =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                Atendimento atendimento =
                        new Atendimento();

                Cliente cliente =
                        new Cliente();

                Funcionario funcionario =
                        new Funcionario();

                atendimento.setIdAtendimento(
                        rs.getInt(
                                "id_atendimento"));

                cliente.setIdCliente(
                        rs.getInt(
                                "id_cliente"));

                funcionario.setIdFuncionario(
                        rs.getInt(
                                "id_funcionario"));

                atendimento.setCliente(
                        cliente);

                atendimento.setFuncionario(
                        funcionario);

                atendimento.setDataAtendimento(
                        rs.getTimestamp(
                                "data_atendimento")
                                .toLocalDateTime());

                atendimento.setObservacao(
                        rs.getString(
                                "observacao"));

                atendimentos.add(
                        atendimento);

            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {

            System.out.println(
                    "Erro: " + e.getMessage());

        }

        return atendimentos;

    }

    // UPDATE
    public void atualizar(Atendimento atendimento) {

        String sql = "UPDATE atendimento\nSET id_cliente = ?,\n    id_funcionario = ?,\n    data_atendimento = ?,\n    observacao = ?\nWHERE id_atendimento = ?\n";

        try {

            Connection conn =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(
                    1,
                    atendimento.getCliente()
                            .getIdCliente());

            stmt.setInt(
                    2,
                    atendimento.getFuncionario()
                            .getIdFuncionario());

            stmt.setTimestamp(
                    3,
                    Timestamp.valueOf(
                            atendimento.getDataAtendimento()));

            stmt.setString(
                    4,
                    atendimento.getObservacao());

            stmt.setInt(
                    5,
                    atendimento.getIdAtendimento());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Atendimento atualizado!");

        } catch (SQLException e) {

            System.out.println(
                    "Erro: " + e.getMessage());

        }

    }

    // DELETE
    public void excluir(int idAtendimento) {

        String sql =
                "DELETE FROM atendimento WHERE id_atendimento = ?";

        try {

            Connection conn =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(1, idAtendimento);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Atendimento removido!");

        } catch (SQLException e) {

            System.out.println(
                    "Erro: " + e.getMessage());

        }

    }

}