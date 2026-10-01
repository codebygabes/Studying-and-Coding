package e2e;

import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Apoio dos testes ao MySQL: devolve o banco "lavajato" a um estado conhecido
 * antes de cada teste e permite conferir o que foi (ou não) gravado.
 * Usa a mesma classe de conexão do sistema (util.Conexao).
 *
 * ATENÇÃO: o reset APAGA todos os dados do banco "lavajato" e recria os dados abaixo.
 */
final class BancoTeste {

    private BancoTeste() {
    }

    static void resetar() {
        try (Connection c = Conexao.conectar(); Statement s = c.createStatement()) {

            s.execute("SET FOREIGN_KEY_CHECKS = 0");
            for (String tabela : new String[]{
                    "atendimento_servico", "atendimento", "servico", "funcionario", "cliente", "usuario"}) {
                s.execute("TRUNCATE TABLE " + tabela);
            }
            s.execute("SET FOREIGN_KEY_CHECKS = 1");

            s.execute("INSERT INTO usuario (login, senha) VALUES ('admin', '1234')");

            s.execute("INSERT INTO cliente (nome, telefone, placa, modelo_veiculo, marca_veiculo) VALUES "
                    + "('Joao', '61996569908', 'ABC1234', 'Civic', 'Honda'),"
                    + "('Maria', '61993469798', 'DFG5678', 'Celta', 'Chevrolet'),"
                    + "('Pedro', '61991234567', 'HIJ9012', 'HB20', 'Hyundai')");

            s.execute("INSERT INTO funcionario (nome, cargo, telefone) VALUES "
                    + "('Carlos', 'Lavador', '61988888888'),"
                    + "('Jose', 'Lavador', '61977777777')");

            s.execute("INSERT INTO servico (nome_servico, descricao, valor) VALUES "
                    + "('Lavagem Completa', 'Lavagem externa e interna', 50.00),"
                    + "('Lavagem Parcial', 'Lavagem externa', 35.00)");

            s.execute("INSERT INTO atendimento (id_cliente, id_funcionario, data_atendimento, observacao) VALUES "
                    + "(1, 1, NOW(), 'Primeiro atendimento teste'),"
                    + "(2, 2, NOW(), 'Segundo atendimento teste'),"
                    + "(3, 1, NOW(), 'Terceiro atendimento teste')");

            s.execute("INSERT INTO atendimento_servico (id_atendimento, id_servico, quantidade) VALUES "
                    + "(1, 1, 1), (2, 2, 1), (3, 2, 1)");

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Não foi possível preparar o banco de teste (o MySQL está ligado e o banco 'lavajato' existe?): "
                            + e.getMessage(), e);
        }
    }

    static long contar(String sql, Object... parametros) {
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            preencher(ps, parametros);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao consultar o banco: " + e.getMessage(), e);
        }
    }

    static String texto(String sql, Object... parametros) {
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            preencher(ps, parametros);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString(1) : null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao consultar o banco: " + e.getMessage(), e);
        }
    }

    /** Insere um registro direto no banco (preparação do cenário) e devolve o ID gerado. */
    static int inserir(String sql, Object... parametros) {
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencher(ps, parametros);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao preparar o cenário no banco: " + e.getMessage(), e);
        }
    }

    private static void preencher(PreparedStatement ps, Object[] parametros) throws SQLException {
        for (int i = 0; i < parametros.length; i++) {
            ps.setObject(i + 1, parametros[i]);
        }
    }
}
