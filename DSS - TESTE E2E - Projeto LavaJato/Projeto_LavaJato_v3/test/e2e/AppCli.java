package e2e;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Executa o sistema de verdade (applications.Main) como um processo separado:
 * "digita" no teclado (entrada padrão) e lê tudo o que aparece no console.
 * É isso que torna o teste End-to-End: CLI -> services -> DAOs -> JDBC -> MySQL.
 */
final class AppCli {

    /** Resultado de uma execução do sistema. */
    record Resultado(int codigoSaida, String entrada, String saida) {

        boolean contem(String trecho) {
            return saida.contains(trecho);
        }

        boolean contemSemCaixa(String trecho) {
            return saida.toLowerCase().contains(trecho.toLowerCase());
        }

        /** Primeira linha do console que contém o trecho (para mensagens de falha legíveis). */
        String linhaCom(String trecho) {
            for (String linha : saida.split("\\R")) {
                if (linha.toLowerCase().contains(trecho.toLowerCase())) {
                    return linha.trim();
                }
            }
            return "(nenhuma)";
        }
    }

    private AppCli() {
    }

    /** Executa com locale en-US (decimais com ponto), para o resultado não depender do PC. */
    static Resultado executar(String... linhas) {
        return executar("en", "US", linhas);
    }

    static Resultado executar(String idioma, String pais, String... linhas) {
        String entrada = String.join("\n", linhas) + "\n";

        List<String> comando = List.of(
                Path.of(System.getProperty("java.home"), "bin", "java").toString(),
                "-Dfile.encoding=UTF-8",
                "-Dstdout.encoding=UTF-8",
                "-Dstderr.encoding=UTF-8",
                "-Duser.language=" + idioma,
                "-Duser.country=" + pais,
                "-cp", classpathDoSistema(),
                "applications.Main");

        try {
            Process processo = new ProcessBuilder(comando).redirectErrorStream(true).start();

            CompletableFuture<byte[]> console = CompletableFuture.supplyAsync(() -> {
                try {
                    return processo.getInputStream().readAllBytes();
                } catch (IOException e) {
                    return new byte[0];
                }
            });

            try (var teclado = processo.getOutputStream()) {
                teclado.write(entrada.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                // O programa pode encerrar (ex.: exceção) antes de ler toda a entrada. Normal.
            }

            if (!processo.waitFor(60, TimeUnit.SECONDS)) {
                processo.destroyForcibly();
                throw new AssertionError("O sistema não encerrou em 60 segundos (travou?).");
            }

            String saida = new String(console.get(5, TimeUnit.SECONDS), StandardCharsets.UTF_8);
            return new Resultado(processo.exitValue(), entrada, saida);

        } catch (AssertionError e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao executar o sistema: " + e, e);
        }
    }

    /**
     * Monta o classpath do processo filho a partir de onde estão as classes do sistema e o driver do MySQL
     * (e não só de java.class.path), para funcionar em qualquer executor de testes (IntelliJ, Maven, console).
     */
    private static String classpathDoSistema() {
        Set<String> partes = new LinkedHashSet<>();
        for (String classe : new String[]{"applications.Main", "com.mysql.cj.jdbc.Driver"}) {
            try {
                Class<?> c = Class.forName(classe, false, AppCli.class.getClassLoader());
                partes.add(Path.of(c.getProtectionDomain().getCodeSource().getLocation().toURI()).toString());
            } catch (Exception e) {
                // não achou por aqui: cai no java.class.path abaixo
            }
        }
        partes.add(System.getProperty("java.class.path"));
        return String.join(File.pathSeparator, partes);
    }
}
