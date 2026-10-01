package e2e;

import e2e.AppCli.Resultado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Funcionários (menu 2: 1 cadastrar, 2 listar, 3 atualizar, 4 excluir). */
class FuncionarioE2ETest extends E2EBase {

    @Test
    @DisplayName("CT-E2E-FUN-001 - Cadastrar funcionário válido")
    void cadastrarFuncionarioValido() {
        Resultado r = rodarNoMenu(2,
                "1", "Marcos Lima", "Lavador", "61988880000",
                "2");

        assertAll(
                () -> assertTrue(r.contem("Funcionário cadastrado!"), "Deveria confirmar o cadastro."),
                () -> assertTrue(r.contem("Marcos Lima"), "O funcionário deveria aparecer na listagem."),
                () -> assertEquals(1, BancoTeste.contar("SELECT COUNT(*) FROM funcionario WHERE nome = ?", "Marcos Lima"),
                        "O funcionário deveria estar gravado no banco."));
    }

    @Test
    @DisplayName("CT-E2E-FUN-002 - Cadastro sem nome")
    void cadastroSemNome() {
        Resultado r = rodarNoMenu(2, "1", "", "Lavador", "61988880000");

        assertAll(
                () -> assertTrue(r.contem("O nome do funcionário é obrigatório."), "Deveria exigir o nome."),
                () -> assertEquals(2, BancoTeste.contar("SELECT COUNT(*) FROM funcionario"),
                        "Nada deveria ser gravado no banco."));
    }

    @Test
    @DisplayName("CT-E2E-FUN-003 - Cadastro sem cargo")
    void cadastroSemCargo() {
        Resultado r = rodarNoMenu(2, "1", "Marcos Lima", "", "61988880000");

        assertAll(
                () -> assertTrue(r.contem("O cargo do funcionário é obrigatório."), "Deveria exigir o cargo."),
                () -> assertEquals(2, BancoTeste.contar("SELECT COUNT(*) FROM funcionario"),
                        "Nada deveria ser gravado no banco."));
    }

    @Test
    @DisplayName("CT-E2E-FUN-004 - Cadastro sem telefone")
    void cadastroSemTelefone() {
        Resultado r = rodarNoMenu(2, "1", "Marcos Lima", "Lavador", "");

        assertAll(
                () -> assertTrue(r.contem("O telefone do funcionário é obrigatório."), "Deveria exigir o telefone."),
                () -> assertEquals(2, BancoTeste.contar("SELECT COUNT(*) FROM funcionario"),
                        "Nada deveria ser gravado no banco."));
    }

    @Test
    @DisplayName("CT-E2E-FUN-005 - Atualizar funcionário válido")
    void atualizarFuncionarioValido() {
        Resultado r = rodarNoMenu(2, "3", "2", "Jose Silva", "Supervisor", "61977770000");

        assertAll(
                () -> assertTrue(r.contem("Funcionário atualizado!"), "Deveria confirmar a atualização."),
                () -> assertEquals("Supervisor", BancoTeste.texto("SELECT cargo FROM funcionario WHERE id_funcionario = 2"),
                        "O novo cargo deveria estar gravado no banco."));
    }

    @Test
    @DisplayName("CT-E2E-FUN-006 - Excluir funcionário sem atendimento")
    void excluirFuncionarioSemAtendimento() {
        int id = BancoTeste.inserir(
                "INSERT INTO funcionario (nome, cargo, telefone) VALUES (?, ?, ?)", "Sem Atendimento", "Lavador", "61900000002");

        Resultado r = rodarNoMenu(2, "4", String.valueOf(id));

        assertAll(
                () -> assertTrue(r.contem("Funcionário removido!"), "Deveria confirmar a remoção."),
                () -> assertEquals(0, BancoTeste.contar("SELECT COUNT(*) FROM funcionario WHERE id_funcionario = ?", id),
                        "O funcionário deveria ter sido removido do banco."));
    }

    @Test
    @Tag("defeito-conhecido")
    @DisplayName("CT-E2E-FUN-007 - Excluir funcionário com atendimento (DEF-E2E-001)")
    void excluirFuncionarioComAtendimento() {
        Resultado r = rodarNoMenu(2, "4", "1");

        assertAll(
                () -> assertEquals(1, BancoTeste.contar("SELECT COUNT(*) FROM funcionario WHERE id_funcionario = 1"),
                        "O funcionário com atendimento deveria continuar no banco."),
                () -> assertSemMensagemTecnicaDoBanco(r));
    }
}
