package e2e;

import e2e.AppCli.Resultado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** HU02 a HU05 aplicadas a Clientes (menu 1: 1 cadastrar, 2 listar, 3 atualizar, 4 excluir). */
class ClienteE2ETest extends E2EBase {

    private static final String INSERIR_CLIENTE =
            "INSERT INTO cliente (nome, telefone, placa, modelo_veiculo, marca_veiculo) VALUES (?, ?, ?, ?, ?)";

    @Test
    @DisplayName("CT-E2E-CLI-001 - Cadastrar cliente válido")
    void cadastrarClienteValido() {
        Resultado r = rodarNoMenu(1,
                "1", "Ana Souza", "61999990000", "QWE1R23", "Onix", "Chevrolet",
                "2");

        assertAll(
                () -> assertTrue(r.contem("Cliente cadastrado!"), "Deveria confirmar o cadastro."),
                () -> assertTrue(r.contem("Ana Souza"), "O cliente deveria aparecer na listagem."),
                () -> assertEquals(1, BancoTeste.contar("SELECT COUNT(*) FROM cliente WHERE placa = ?", "QWE1R23"),
                        "O cliente deveria estar gravado no banco."));
    }

    @Test
    @DisplayName("CT-E2E-CLI-002 - Cadastrar placa inválida")
    void cadastrarPlacaInvalida() {
        Resultado r = rodarNoMenu(1,
                "1", "Ana Souza", "61999990000", "123", "Onix", "Chevrolet");

        assertAll(
                () -> assertTrue(r.contem("Placa inválida"), "Deveria rejeitar a placa."),
                () -> assertFalse(r.contem("Cliente cadastrado!"), "Não deveria confirmar o cadastro."),
                () -> assertEquals(0, BancoTeste.contar("SELECT COUNT(*) FROM cliente WHERE nome = ?", "Ana Souza"),
                        "Nada deveria ser gravado no banco."));
    }

    @Test
    @DisplayName("CT-E2E-CLI-003 - Atualizar cliente com campos vazios")
    void atualizarComCamposVazios() {
        Resultado r = rodarNoMenu(1,
                "3", "2", "", "", "", "", "");

        assertAll(
                () -> assertTrue(r.contem("O nome do cliente é obrigatório."), "Deveria exigir o nome."),
                () -> assertFalse(r.contem("Cliente atualizado!"), "Não deveria confirmar a atualização."),
                () -> assertEquals("Maria", BancoTeste.texto("SELECT nome FROM cliente WHERE id_cliente = 2"),
                        "Os dados do cliente não deveriam mudar."));
    }

    @Test
    @DisplayName("CT-E2E-CLI-004 - Atualizar cliente válido")
    void atualizarClienteValido() {
        Resultado r = rodarNoMenu(1,
                "3", "2", "Maria Silva", "61911112222", "DFG5678", "Celta", "Chevrolet",
                "2");

        assertAll(
                () -> assertTrue(r.contem("Cliente atualizado!"), "Deveria confirmar a atualização."),
                () -> assertEquals("Maria Silva", BancoTeste.texto("SELECT nome FROM cliente WHERE id_cliente = 2"),
                        "O novo nome deveria estar gravado no banco."),
                () -> assertTrue(r.contem("Maria Silva"), "Os dados atualizados deveriam aparecer na consulta."));
    }

    @Test
    @DisplayName("CT-E2E-CLI-005 - Excluir cliente sem atendimento")
    void excluirClienteSemAtendimento() {
        int id = BancoTeste.inserir(INSERIR_CLIENTE, "Sem Atendimento", "61900000001", "ZZZ9999", "Gol", "VW");

        Resultado r = rodarNoMenu(1, "4", String.valueOf(id));

        assertAll(
                () -> assertTrue(r.contem("Cliente removido!"), "Deveria confirmar a remoção."),
                () -> assertEquals(0, BancoTeste.contar("SELECT COUNT(*) FROM cliente WHERE id_cliente = ?", id),
                        "O cliente deveria ter sido removido do banco."));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-CLI-006 - Excluir cliente com atendimento (DEF-E2E-001)")
    void excluirClienteComAtendimento() {
        Resultado r = rodarNoMenu(1, "4", "1");

        assertAll(
                () -> assertEquals(1, BancoTeste.contar("SELECT COUNT(*) FROM cliente WHERE id_cliente = 1"),
                        "O cliente com atendimento deveria continuar no banco."),
                () -> assertSemMensagemTecnicaDoBanco(r));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-CLI-007 - Excluir ID inexistente (DEF-E2E-002)")
    void excluirIdInexistente() {
        Resultado r = rodarNoMenu(1, "4", "9999");

        assertAll(
                () -> assertFalse(r.contem("Cliente removido!"),
                        "Informou sucesso para um ID que não existe."),
                () -> assertTrue(r.contemSemCaixa("não encontrad") || r.contemSemCaixa("nenhum registro"),
                        "Deveria informar que nenhum registro foi encontrado."));
    }
}
