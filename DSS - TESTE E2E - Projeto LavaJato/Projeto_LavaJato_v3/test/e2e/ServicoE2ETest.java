package e2e;

import e2e.AppCli.Resultado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Serviços (menu 3: 1 cadastrar, 2 listar, 3 atualizar, 4 excluir). */
class ServicoE2ETest extends E2EBase {

    private static final String INSERIR_SERVICO =
            "INSERT INTO servico (nome_servico, descricao, valor) VALUES (?, ?, ?)";

    @Test
    @DisplayName("CT-E2E-SER-001 - Cadastrar serviço válido")
    void cadastrarServicoValido() {
        Resultado r = rodarNoMenu(3,
                "1", "Polimento", "Polimento completo", "99.90",
                "2");

        assertAll(
                () -> assertTrue(r.contem("Serviço cadastrado!"), "Deveria confirmar o cadastro."),
                () -> assertTrue(r.contem("Polimento"), "O serviço deveria aparecer na listagem."),
                () -> assertEquals("99.90",
                        BancoTeste.texto("SELECT valor FROM servico WHERE nome_servico = ?", "Polimento"),
                        "O serviço deveria estar gravado no banco com o valor informado."));
    }

    @Test
    @DisplayName("CT-E2E-SER-002 - Cadastro sem nome")
    void cadastroSemNome() {
        Resultado r = rodarNoMenu(3, "1", "", "Descricao", "50.00");

        assertAll(
                () -> assertTrue(r.contem("O nome do serviço é obrigatório."), "Deveria exigir o nome."),
                () -> assertEquals(2, BancoTeste.contar("SELECT COUNT(*) FROM servico"),
                        "Nada deveria ser gravado no banco."));
    }

    @Test
    @DisplayName("CT-E2E-SER-003 - Cadastro com valor zero")
    void cadastroComValorZero() {
        Resultado r = rodarNoMenu(3, "1", "Teste", "Descricao", "0");

        assertAll(
                () -> assertTrue(r.contem("O valor do serviço deve ser maior que zero."), "Deveria rejeitar o valor."),
                () -> assertEquals(0, BancoTeste.contar("SELECT COUNT(*) FROM servico WHERE nome_servico = ?", "Teste"),
                        "Nada deveria ser gravado no banco."));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-SER-004 - Entrada não numérica no valor (DEF-E2E-003)")
    void entradaNaoNumericaNoValor() {
        Resultado r = rodarNoMenu(3, "1", "Teste", "Descricao", "abc");

        assertNaoEncerrouPorExcecao(r);
    }

    @Test
    @DisplayName("CT-E2E-SER-005 - Atualizar serviço válido")
    void atualizarServicoValido() {
        Resultado r = rodarNoMenu(3, "3", "2", "Lavagem Parcial Plus", "Lavagem externa e cera", "40.00");

        assertAll(
                () -> assertTrue(r.contem("Serviço atualizado!"), "Deveria confirmar a atualização."),
                () -> assertEquals("40.00", BancoTeste.texto("SELECT valor FROM servico WHERE id_servico = 2"),
                        "O novo valor deveria estar gravado no banco."));
    }

    @Test
    @DisplayName("CT-E2E-SER-006 - Excluir serviço sem vínculo")
    void excluirServicoSemVinculo() {
        int id = BancoTeste.inserir(INSERIR_SERVICO, "Sem Vinculo", "Descricao", 10.00);

        Resultado r = rodarNoMenu(3, "4", String.valueOf(id));

        assertAll(
                () -> assertTrue(r.contem("Serviço removido!"), "Deveria confirmar a remoção."),
                () -> assertEquals(0, BancoTeste.contar("SELECT COUNT(*) FROM servico WHERE id_servico = ?", id),
                        "O serviço deveria ter sido removido do banco."));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-SER-007 - Excluir serviço com vínculo (DEF-E2E-001)")
    void excluirServicoComVinculo() {
        Resultado r = rodarNoMenu(3, "4", "1");

        assertAll(
                () -> assertEquals(1, BancoTeste.contar("SELECT COUNT(*) FROM servico WHERE id_servico = 1"),
                        "O serviço vinculado deveria continuar no banco."),
                () -> assertSemMensagemTecnicaDoBanco(r));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-SER-008 - Excluir ID inexistente (DEF-E2E-002)")
    void excluirIdInexistente() {
        Resultado r = rodarNoMenu(3, "4", "9999");

        assertAll(
                () -> assertFalse(r.contem("Serviço removido!"),
                        "Informou sucesso para um ID que não existe."),
                () -> assertTrue(r.contemSemCaixa("não encontrad") || r.contemSemCaixa("nenhum registro"),
                        "Deveria informar que nenhum registro foi encontrado."));
    }

    // Caso extra: a tela pede "Valor (ex: 49.90)", mas o Scanner usa o formato do idioma do computador.

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-SER-009 - Valor no formato do exemplo da tela (49.90) em computador pt-BR")
    void valorNoFormatoDoExemploEmPtBr() {
        Resultado r = rodarComLocale("pt", "BR",
                "admin", "1234", "3", "1", "Encerado", "Cera", "49.90", "0", "0", "0", "0");

        assertAll(
                () -> assertNaoEncerrouPorExcecao(r),
                () -> assertEquals(1, BancoTeste.contar("SELECT COUNT(*) FROM servico WHERE nome_servico = ?", "Encerado"),
                        "O valor no formato do exemplo mostrado na tela (49.90) deveria ser aceito."));
    }
}
