package e2e;

import e2e.AppCli.Resultado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Base dos testes E2E: reseta o banco antes de cada teste, executa o sistema
 * e salva o que foi digitado e o que apareceu no console como evidência
 * em evidencias/execucoes/<ID-do-caso>.txt
 */
abstract class E2EBase {

    private String caso;
    private int execucoes;

    @BeforeEach
    void prepararCenario(TestInfo info) {
        caso = info.getDisplayName();
        execucoes = 0;
        BancoTeste.resetar();
    }

    // ---------- execução do sistema ----------

    /** Executa o sistema digitando exatamente as linhas informadas. */
    protected Resultado rodar(String... linhas) {
        return registrar(AppCli.executar(linhas));
    }

    protected Resultado rodarComLocale(String idioma, String pais, String... linhas) {
        return registrar(AppCli.executar(idioma, pais, linhas));
    }

    /**
     * Faz login (admin / 1234), entra no menu principal escolhido (1 clientes, 2 funcionários,
     * 3 serviços, 4 atendimentos), executa os passos digitados no submenu e depois volta e sai.
     */
    protected Resultado rodarNoMenu(int menu, String... passosDoSubmenu) {
        String[] linhas = new String[2 + 1 + passosDoSubmenu.length + 4];
        int i = 0;
        linhas[i++] = "admin";
        linhas[i++] = "1234";
        linhas[i++] = String.valueOf(menu);
        for (String passo : passosDoSubmenu) {
            linhas[i++] = passo;
        }
        // voltar ao menu principal e sair (zeros extras só garantem a saída em caso de reentrada)
        for (int k = 0; k < 4; k++) {
            linhas[i++] = "0";
        }
        return rodar(linhas);
    }

    // ---------- verificações reutilizadas ----------

    /** O usuário não deve ver mensagens técnicas do MySQL (DEF-E2E-001). */
    protected static void assertSemMensagemTecnicaDoBanco(Resultado r) {
        String s = r.saida().toLowerCase();
        boolean tecnica = s.contains("cannot delete or update")
                || s.contains("cannot add or update")
                || s.contains("foreign key")
                || s.contains("constraint");
        assertFalse(tecnica, "O usuário viu uma mensagem técnica do banco em vez de uma mensagem amigável: \""
                + r.linhaCom("Erro:") + "\"");
    }

    /** O sistema não pode encerrar por exceção não tratada (DEF-E2E-003, DEF-E2E-005). */
    protected static void assertNaoEncerrouPorExcecao(Resultado r) {
        assertFalse(r.contem("Exception"), "Exceção não tratada: \"" + r.linhaCom("Exception") + "\"");
        assertEquals(0, r.codigoSaida(), "O sistema encerrou com código de saída " + r.codigoSaida()
                + " (esperado 0, encerramento normal).");
    }

    // ---------- evidência ----------

    private Resultado registrar(Resultado r) {
        execucoes++;
        try {
            String id = caso.split(" - ")[0].replaceAll("[^A-Za-z0-9_-]", "_");
            Path pasta = Path.of("evidencias", "execucoes");
            Files.createDirectories(pasta);
            String nome = id + (execucoes > 1 ? "-" + execucoes : "") + ".txt";
            String texto = "Caso: " + caso + "\n"
                    + "Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n"
                    + "Código de saída: " + r.codigoSaida() + "\n\n"
                    + "=== ENTRADA (o que foi digitado) ===\n" + r.entrada() + "\n"
                    + "=== SAÍDA (o que apareceu no console) ===\n" + r.saida();
            Files.writeString(pasta.resolve(nome), texto, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // evidência é um complemento: não derruba o teste
            System.err.println("Não foi possível salvar a evidência: " + e.getMessage());
        }
        return r;
    }
}
