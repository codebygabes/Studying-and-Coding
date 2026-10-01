package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import util.Conexao;

public class ClienteDAO {

    // CREATE
    public void inserir(Cliente cliente) {

        String sql = "INSERT INTO cliente\n(nome, telefone, placa, modelo_veiculo, marca_veiculo)\nVALUES (?, ?, ?, ?, ?)\n";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getPlaca());
            stmt.setString(4, cliente.getModeloVeiculo());
            stmt.setString(5, cliente.getMarcaVeiculo());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Cliente cadastrado!");

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

    // READ
    public List<Cliente> listar() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM cliente";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(
                        rs.getInt("id_cliente"));

                cliente.setNome(
                        rs.getString("nome"));

                cliente.setTelefone(
                        rs.getString("telefone"));

                cliente.setPlaca(
                        rs.getString("placa"));

                cliente.setModeloVeiculo(
                        rs.getString("modelo_veiculo"));

                cliente.setMarcaVeiculo(
                        rs.getString("marca_veiculo"));

                clientes.add(cliente);

            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

        return clientes;

    }

    // UPDATE
    public void atualizar(Cliente cliente) {

        String sql = "UPDATE cliente\nSET nome = ?,\n    telefone = ?,\n    placa = ?,\n    modelo_veiculo = ?,\n    marca_veiculo = ?\nWHERE id_cliente = ?\n";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getPlaca());
            stmt.setString(4, cliente.getModeloVeiculo());
            stmt.setString(5, cliente.getMarcaVeiculo());

            stmt.setInt(6, cliente.getIdCliente());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Cliente atualizado!");

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

    // DELETE
    public void excluir(int idCliente) {

        String sql =
                "DELETE FROM cliente WHERE id_cliente = ?";

        try {

            Connection conn = Conexao.conectar();

            PreparedStatement stmt =
                    conn.prepareStatement(sql);

            stmt.setInt(1, idCliente);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            System.out.println("Cliente removido!");

        }
        catch (SQLException e) {

            System.out.println("Erro: " + e.getMessage());

        }

    }

}