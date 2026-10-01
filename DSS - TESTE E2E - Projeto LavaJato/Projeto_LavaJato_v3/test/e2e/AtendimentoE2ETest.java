package e2e;

import e2e.AppCli.Resultado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Atendimentos (menu 4: 1 cadastrar, 2 listar, 3 atualizar, 4 excluir). */
class AtendimentoE2ETest extends E2EBase {

    private static final String CONTAR_ATENDIMENTOS_DA_OBS =
            "SELECT COUNT(*) FROM atendimento WHERE observacao = ?";
    private static final String CONTAR_SERVICOS_VINCULADOS_DA_OBS =
            "SELECT COUNT(*) FROM atendimento_servico asv "
                    + "JOIN atendimento a ON a.id_atendimento = asv.id_atendimento WHERE a.observacao = ?";

    @Test
    @DisplayName("CT-E2E-ATE-001 - Listar atendimentos")
    void listarAtendimentos() {
        Resultado r = rodarNoMenu(4, "2");

        assertAll(
                () -> assertTrue(r.contem("Primeiro atendimento teste")),
                () -> assertTrue(r.contem("Segundo atendimento teste")),
                () -> assertTrue(r.contem("Terceiro atendimento teste")),
                () -> assertEquals(0, r.codigoSaida(), "Deveria encerrar normalmente."));
    }

    // Fluxo do cadastro: ID do cliente, ID do funcionário, observação,
    // depois (ID do serviço, quantidade, "adicionar outro?") para cada serviço.

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-ATE-002 - Cadastrar atendimento (DEF-E2E-005)")
    void cadastrarAtendimento() {
        Resultado r = rodarNoMenu(4,
                "1", "2", "1", "Lavagem rapida", "1", "1", "N");

        assertAll(
                () -> assertNaoEncerrouPorExcecao(r),
                () -> assertEquals(1, BancoTeste.contar(CONTAR_ATENDIMENTOS_DA_OBS, "Lavagem rapida"),
                        "O atendimento deveria estar gravado."),
                () -> assertEquals(1, BancoTeste.contar(CONTAR_SERVICOS_VINCULADOS_DA_OBS, "Lavagem rapida"),
                        "O serviço deveria estar vinculado ao atendimento."));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-ATE-008 - Cadastro de atendimento não deixa registro parcial (DEF-E2E-004)")
    void cadastroNaoDeixaRegistroParcial() {
        rodarNoMenu(4, "1", "2", "1", "Lavagem rapida", "1", "1", "N");

        long atendimentos = BancoTeste.contar(CONTAR_ATENDIMENTOS_DA_OBS, "Lavagem rapida");
        long vinculos = BancoTeste.contar(CONTAR_SERVICOS_VINCULADOS_DA_OBS, "Lavagem rapida");

        assertEquals(atendimentos, vinculos,
                "Atendimento gravado sem seus serviços (" + atendimentos + " atendimento(s), "
                        + vinculos + " vínculo(s)): ou grava tudo ou não grava nada.");
    }

    @Test
    @DisplayName("CT-E2E-ATE-003 - Atualizar atendimento válido")
    void atualizarAtendimentoValido() {
        Resultado r = rodarNoMenu(4, "3", "2", "1", "2", "Obs atualizada");

        assertAll(
                () -> assertTrue(r.contem("Atendimento atualizado!"), "Deveria confirmar a atualização."),
                () -> assertEquals("Obs atualizada",
                        BancoTeste.texto("SELECT observacao FROM atendimento WHERE id_atendimento = 2")),
                () -> assertEquals("1", BancoTeste.texto("SELECT id_cliente FROM atendimento WHERE id_atendimento = 2")),
                () -> assertEquals("2", BancoTeste.texto("SELECT id_funcionario FROM atendimento WHERE id_atendimento = 2")));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-ATE-004 - Atualizar com relacionamento inválido (DEF-E2E-001)")
    void atualizarComRelacionamentoInvalido() {
        Resultado r = rodarNoMenu(4, "3", "2", "999", "1", "Obs invalida");

        assertAll(
                () -> assertEquals("Segundo atendimento teste",
                        BancoTeste.texto("SELECT observacao FROM atendimento WHERE id_atendimento = 2"),
                        "O atendimento não deveria ter sido alterado."),
                () -> assertSemMensagemTecnicaDoBanco(r));
    }

    @Test
    @DisplayName("CT-E2E-ATE-005 - Excluir atendimento sem dependência")
    void excluirAtendimentoSemDependencia() {
        int id = BancoTeste.inserir(
                "INSERT INTO atendimento (id_cliente, id_funcionario, data_atendimento, observacao) "
                        + "VALUES (1, 1, NOW(), 'Sem servicos')");

        Resultado r = rodarNoMenu(4, "4", String.valueOf(id));

        assertAll(
                () -> assertTrue(r.contem("Atendimento removido!"), "Deveria confirmar a remoção."),
                () -> assertEquals(0, BancoTeste.contar("SELECT COUNT(*) FROM atendimento WHERE id_atendimento = ?", id),
                        "O atendimento deveria ter sido removido do banco."));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-ATE-006 - Excluir atendimento com serviço associado (DEF-E2E-001)")
    void excluirAtendimentoComServicoAssociado() {
        Resultado r = rodarNoMenu(4, "4", "1");

        assertAll(
                () -> assertEquals(1, BancoTeste.contar("SELECT COUNT(*) FROM atendimento WHERE id_atendimento = 1"),
                        "O atendimento com serviços deveria continuar no banco."),
                () -> assertSemMensagemTecnicaDoBanco(r));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-ATE-007 - Entrada inválida (texto no lugar do ID) (DEF-E2E-003)")
    void entradaInvalida() {
        Resultado r = rodarNoMenu(4, "4", "abc");

        assertNaoEncerrouPorExcecao(r);
    }
}
