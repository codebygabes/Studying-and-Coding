package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.AtendimentoServico;
import util.Conexao;

public class AtendimentoServicoDAO {

    // CREATE
    public void inserir(
            AtendimentoServico atendimentoServico) {

        String sql = "INSERT INTO atendimento_servico\n(id_atendimento,\n id_servico,\n quantidade)\nVALUES (?, ?, ?)\n";

        try {

            Connection conn =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(
                    1,
                    atendimentoServico
                    .getAtendimento()
                    .getIdAtendimento());

            stmt.setInt(
                    2,
                    atendimentoServico
                    .getServico()
                    .getIdServico());

            stmt.setInt(
                    3,
                    atendimentoServico
                    .getQuantidade());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println(
                    "Serviço vinculado ao atendimento!");

        }
        catch(SQLException e) {

            System.out.println(
                    "Erro: " + e.getMessage());

        }

    }

    // DELETE
    public void excluir(
            int idAtendimento,
            int idServico) {

        String sql = "DELETE FROM atendimento_servico\nWHERE id_atendimento = ?\nAND id_servico = ?\n";

        try {

            Connection conn =
                    Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(1, idAtendimento);
            stmt.setInt(2, idServico);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        }
        catch(SQLException e) {

            System.out.println(
                    "Erro: " + e.getMessage());

        }

    }

}