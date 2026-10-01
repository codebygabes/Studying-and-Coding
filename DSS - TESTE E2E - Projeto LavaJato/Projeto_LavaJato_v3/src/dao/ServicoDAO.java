package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import model.Servico;
import util.Conexao;

public class ServicoDAO {

    // CREATE
    public void inserir(Servico servico) {

        String sql = "INSERT INTO servico\n(nome_servico, descricao, valor)\nVALUES (?, ?, ?)\n";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, servico.getNomeServico());
            stmt.setString(2, servico.getDescricao());
            stmt.setDouble(3, servico.getValor());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Serviço cadastrado!");

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

    // READ
    public List<Servico> listar() {

        List<Servico> servicos = new ArrayList<>();

        String sql = "SELECT * FROM servico";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Servico servico = new Servico();

                servico.setIdServico(
                        rs.getInt("id_servico"));

                servico.setNomeServico(
                        rs.getString("nome_servico"));

                servico.setDescricao(
                        rs.getString("descricao"));

                servico.setValor(
                        rs.getDouble("valor"));

                servicos.add(servico);

            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

        return servicos;

    }

    // UPDATE
    public void atualizar(Servico servico) {

        String sql = "UPDATE servico\nSET nome_servico = ?,\n    descricao = ?,\n    valor = ?\nWHERE id_servico = ?\n";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, servico.getNomeServico());
            stmt.setString(2, servico.getDescricao());
            stmt.setDouble(3, servico.getValor());

            stmt.setInt(4, servico.getIdServico());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Serviço atualizado!");

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

    // DELETE
    public void excluir(int idServico) {

        String sql =
                "DELETE FROM servico WHERE id_servico = ?";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(1, idServico);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Serviço removido!");

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

}