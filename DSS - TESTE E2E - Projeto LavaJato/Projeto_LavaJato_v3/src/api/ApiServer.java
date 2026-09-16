package api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import model.Cliente;
import model.Funcionario;
import model.Servico;
import services.AutenticacaoService;
import services.ClienteService;
import services.FuncionarioService;
import services.ServicoService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * API HTTP bem simples do sistema Lava-Jato.
 *
 * Feita só com recursos nativos do JDK (com.sun.net.httpserver) -
 * não usa nenhuma biblioteca/framework externo (sem Spring, sem Gson, etc).
 * Reaproveita as mesmas regras de negócio (Service) já usadas pelo CLI.
 *
 * Como rodar: execute o main desta classe. A API sobe em
 * http://localhost:8080
 *
 * Endpoints disponíveis:
 *   POST /login          -> { "login": "...", "senha": "..." }
 *   GET  /clientes        -> lista todos os clientes
 *   POST /clientes        -> { "nome":"...", "telefone":"...", "placa":"...",
 *                              "modeloVeiculo":"...", "marcaVeiculo":"..." }
 *   GET  /funcionarios    -> lista todos os funcionários
 *   POST /funcionarios    -> { "nome":"...", "cargo":"...", "telefone":"..." }
 *   GET  /servicos        -> lista todos os serviços
 *   POST /servicos        -> { "nomeServico":"...", "descricao":"...", "valor":49.90 }
 */
public class ApiServer {

    static ClienteService clienteService = new ClienteService();
    static FuncionarioService funcionarioService = new FuncionarioService();
    static ServicoService servicoService = new ServicoService();
    static AutenticacaoService autenticacaoService = new AutenticacaoService();

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/login", ApiServer::tratarLogin);
        server.createContext("/clientes", ApiServer::tratarClientes);
        server.createContext("/funcionarios", ApiServer::tratarFuncionarios);
        server.createContext("/servicos", ApiServer::tratarServicos);

        server.setExecutor(null); // usa o executor padrão do JDK

        server.start();

        System.out.println("✅ API rodando em http://localhost:8080");
        System.out.println("   POST /login | GET+POST /clientes | GET+POST /funcionarios | GET+POST /servicos");
    }

    // ===================== LOGIN =====================

    static void tratarLogin(HttpExchange exchange) throws IOException {

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            responder(exchange, 405, "{\"erro\":\"Método não permitido. Use POST.\"}");
            return;
        }

        Map<String, String> dados = JsonUtil.parseObjetoSimples(lerCorpo(exchange));

        String login = dados.get("login");
        String senha = dados.get("senha");

        try {

            boolean autenticado = autenticacaoService.login(login, senha);

            if (autenticado) {
                responder(exchange, 200, "{\"autenticado\":true}");
            } else {
                responder(exchange, 401, "{\"autenticado\":false,\"mensagem\":\"Login ou senha inválidos.\"}");
            }

        } catch (IllegalArgumentException e) {
            responder(exchange, 400, "{\"erro\":\"" + JsonUtil.escapar(e.getMessage()) + "\"}");
        }
    }

    // ===================== CLIENTES =====================

    static void tratarClientes(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(metodo)) {

            List<Cliente> clientes = clienteService.listarClientes();
            responder(exchange, 200, JsonUtil.clientesParaJson(clientes));

        } else if ("POST".equalsIgnoreCase(metodo)) {

            Map<String, String> dados = JsonUtil.parseObjetoSimples(lerCorpo(exchange));

            Cliente c = new Cliente();
            c.setNome(dados.get("nome"));
            c.setTelefone(dados.get("telefone"));
            c.setPlaca(dados.get("placa"));
            c.setModeloVeiculo(dados.get("modeloVeiculo"));
            c.setMarcaVeiculo(dados.get("marcaVeiculo"));

            try {
                clienteService.cadastrarCliente(c);
                responder(exchange, 201, "{\"mensagem\":\"Cliente cadastrado com sucesso.\"}");
            } catch (IllegalArgumentException e) {
                responder(exchange, 400, "{\"erro\":\"" + JsonUtil.escapar(e.getMessage()) + "\"}");
            }

        } else {
            responder(exchange, 405, "{\"erro\":\"Método não permitido. Use GET ou POST.\"}");
        }
    }

    // ===================== FUNCIONÁRIOS =====================

    static void tratarFuncionarios(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(metodo)) {

            List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
            responder(exchange, 200, JsonUtil.funcionariosParaJson(funcionarios));

        } else if ("POST".equalsIgnoreCase(metodo)) {

            Map<String, String> dados = JsonUtil.parseObjetoSimples(lerCorpo(exchange));

            Funcionario f = new Funcionario();
            f.setNome(dados.get("nome"));
            f.setCargo(dados.get("cargo"));
            f.setTelefone(dados.get("telefone"));

            try {
                funcionarioService.cadastrarFuncionario(f);
                responder(exchange, 201, "{\"mensagem\":\"Funcionário cadastrado com sucesso.\"}");
            } catch (IllegalArgumentException e) {
                responder(exchange, 400, "{\"erro\":\"" + JsonUtil.escapar(e.getMessage()) + "\"}");
            }

        } else {
            responder(exchange, 405, "{\"erro\":\"Método não permitido. Use GET ou POST.\"}");
        }
    }

    // ===================== SERVIÇOS =====================

    static void tratarServicos(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(metodo)) {

            List<Servico> servicos = servicoService.listarServicos();
            responder(exchange, 200, JsonUtil.servicosParaJson(servicos));

        } else if ("POST".equalsIgnoreCase(metodo)) {

            Map<String, String> dados = JsonUtil.parseObjetoSimples(lerCorpo(exchange));

            Servico s = new Servico();
            s.setNomeServico(dados.get("nomeServico"));
            s.setDescricao(dados.get("descricao"));

            try {

                String valorTexto = dados.get("valor");
                s.setValor(valorTexto != null ? Double.parseDouble(valorTexto) : 0);

                servicoService.cadastrarServico(s);
                responder(exchange, 201, "{\"mensagem\":\"Serviço cadastrado com sucesso.\"}");

            } catch (IllegalArgumentException e) {
                // Cobre também NumberFormatException, que é subclasse de IllegalArgumentException
                responder(exchange, 400, "{\"erro\":\"" + JsonUtil.escapar(e.getMessage()) + "\"}");
            }

        } else {
            responder(exchange, 405, "{\"erro\":\"Método não permitido. Use GET ou POST.\"}");
        }
    }

    // ===================== AUXILIARES =====================

    static String lerCorpo(HttpExchange exchange) throws IOException {
        Scanner scanner = new Scanner(exchange.getRequestBody(), "UTF-8");
        scanner.useDelimiter("\\A");
        String corpo = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        return corpo;
    }

    static void responder(HttpExchange exchange, int status, String corpoJson) throws IOException {

        byte[] bytes = corpoJson.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
