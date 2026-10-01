package e2e;

import e2e.AppCli.Resultado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** HU01 – Realizar login + inicialização e encerramento do sistema. */
class AutenticacaoE2ETest extends E2EBase {

    @Test
    @DisplayName("CT-E2E-001 - Inicializar com banco disponível")
    void inicializarComBancoDisponivel() {
        Resultado r = rodar("admin", "1234", "0");

        assertAll(
                () -> assertTrue(r.contem("Banco de dados conectado com sucesso"), "Deveria conectar ao banco."),
                () -> assertTrue(r.contem("LOGIN - LAVA-JATO"), "Deveria exibir a tela de login."));
    }

    @Test
    @DisplayName("CT-E2E-002 - Login válido")
    void loginValido() {
        Resultado r = rodar("admin", "1234", "0");

        assertAll(
                () -> assertTrue(r.contem("Login realizado com sucesso!"), "Deveria autenticar o usuário."),
                () -> assertTrue(r.contem("SISTEMA LAVA-JATO - MENU"), "Deveria exibir o menu principal."));
    }

    @Test
    @DisplayName("CT-E2E-003 - Encerrar o sistema")
    void encerrarSistema() {
        Resultado r = rodar("admin", "1234", "0");

        assertAll(
                () -> assertTrue(r.contem("Saindo do sistema"), "Deveria exibir a mensagem de saída."),
                () -> assertEquals(0, r.codigoSaida(), "Deveria encerrar normalmente (código 0)."));
    }

    // Casos extras (cenário 2 da HU01 – credenciais inválidas)

    @Test
    @DisplayName("CT-E2E-004 - Login com senha inválida")
    void loginComSenhaInvalida() {
        Resultado r = rodar("admin", "senha-errada");

        assertAll(
                () -> assertTrue(r.contem("Login ou senha incorretos."), "Deveria negar o acesso."),
                () -> assertFalse(r.contem("SISTEMA LAVA-JATO - MENU"), "Não deveria exibir o menu principal."),
                () -> assertEquals(0, r.codigoSaida()));
    }

    @Test
    @DisplayName("CT-E2E-005 - Login com campos vazios")
    void loginComCamposVazios() {
        Resultado r = rodar("", "");

        assertAll(
                () -> assertTrue(r.contem("O login é obrigatório."), "Deveria exigir o login."),
                () -> assertFalse(r.contem("SISTEMA LAVA-JATO - MENU"), "Não deveria exibir o menu principal."),
                () -> assertEquals(0, r.codigoSaida()));
    }
}
