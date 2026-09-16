package applications;

import model.Atendimento;
import model.AtendimentoServico;
import model.Cliente;
import model.Funcionario;
import model.Servico;
import services.AtendimentoService;
import services.ClienteService;
import services.FuncionarioService;
import services.ServicoService;
import services.AutenticacaoService;
import util.Conexao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ClienteService clienteService = new ClienteService();
    static FuncionarioService funcionarioService = new FuncionarioService();
    static ServicoService servicoService = new ServicoService();
    static AtendimentoService atendimentoService = new AtendimentoService();
    static AutenticacaoService autenticacaoService = new AutenticacaoService();

    public static void main(String[] args) {

        // Testa conexão antes de tudo
        try {
            Conexao.conectar();
            System.out.println("✅ Banco de dados conectado com sucesso!\n");
        } catch (Exception e) {
            System.out.println("❌ Erro ao conectar ao banco: " + e.getMessage());
            return;
        }

        if (!fazerLogin()) {
            System.out.println("\nAcesso encerrado.");
            return;
        }

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("========================================");
            System.out.println("       SISTEMA LAVA-JATO - MENU        ");
            System.out.println("========================================");
            System.out.println("  1 - Gerenciar Clientes");
            System.out.println("  2 - Gerenciar Funcionários");
            System.out.println("  3 - Gerenciar Serviços");
            System.out.println("  4 - Gerenciar Atendimentos");
            System.out.println("  0 - Sair");
            System.out.println("========================================");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    menuCliente();
                    break;
                case 2:
                    menuFuncionario();
                    break;
                case 3:
                    menuServico();
                    break;
                case 4:
                    menuAtendimento();
                    break;
                case 0:
                    System.out.println("\nSaindo do sistema. Até logo!");
                    break;
                default:
                    System.out.println("❌ Opção inválida!\n");
                    break;
            }
        }
    }

    // ===================== LOGIN =====================

    static boolean fazerLogin() {

        System.out.println("========================================");
        System.out.println("          LOGIN - LAVA-JATO             ");
        System.out.println("========================================");

        System.out.print("Login: ");
        String login = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        try {
            boolean autenticado = autenticacaoService.login(login, senha);

            if (autenticado) {
                System.out.println("\nLogin realizado com sucesso!\n");
                return true;
            }

            System.out.println("\nLogin ou senha incorretos.\n");
            return false;

        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ " + e.getMessage());
            return false;
        }
    }

    // ===================== MENU CLIENTE =====================

    static void menuCliente() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n--- CLIENTES ---");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Atualizar cliente");
            System.out.println("4 - Excluir cliente");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    excluirCliente();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
                    break;
            }
        }
    }

    static void cadastrarCliente() {

        System.out.println("\n-- Cadastrar Cliente --");

        Cliente c = new Cliente();

        System.out.print("Nome: ");
        c.setNome(sc.nextLine());

        System.out.print("Telefone: ");
        c.setTelefone(sc.nextLine());

        System.out.print("Placa do veículo: ");
        c.setPlaca(sc.nextLine());

        System.out.print("Modelo do veículo: ");
        c.setModeloVeiculo(sc.nextLine());

        System.out.print("Marca do veículo: ");
        c.setMarcaVeiculo(sc.nextLine());

        try {
            clienteService.cadastrarCliente(c);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void listarClientes() {

        System.out.println("\n-- Lista de Clientes --");

        List<Cliente> clientes = clienteService.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-5s %-20s %-15s %-10s %-12s %-12s%n",
                "ID", "Nome", "Telefone", "Placa", "Modelo", "Marca");
        System.out.println("---------------------------------------------------------------");

        for (Cliente c : clientes) {
            System.out.printf("%-5d %-20s %-15s %-10s %-12s %-12s%n",
                    c.getIdCliente(),
                    c.getNome(),
                    c.getTelefone(),
                    c.getPlaca(),
                    c.getModeloVeiculo(),
                    c.getMarcaVeiculo());
        }

        System.out.println("---------------------------------------------------------------");
    }

    static void atualizarCliente() {

        listarClientes();

        System.out.print("\nDigite o ID do cliente a atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Cliente c = new Cliente();
        c.setIdCliente(id);

        System.out.print("Novo nome: ");
        c.setNome(sc.nextLine());

        System.out.print("Novo telefone: ");
        c.setTelefone(sc.nextLine());

        System.out.print("Nova placa: ");
        c.setPlaca(sc.nextLine());

        System.out.print("Novo modelo: ");
        c.setModeloVeiculo(sc.nextLine());

        System.out.print("Nova marca: ");
        c.setMarcaVeiculo(sc.nextLine());

        try {
            clienteService.atualizarCliente(c);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void excluirCliente() {

        listarClientes();

        System.out.print("\nDigite o ID do cliente a excluir: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            clienteService.excluirCliente(id);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ===================== MENU FUNCIONÁRIO =====================

    static void menuFuncionario() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n--- FUNCIONÁRIOS ---");
            System.out.println("1 - Cadastrar funcionário");
            System.out.println("2 - Listar funcionários");
            System.out.println("3 - Atualizar funcionário");
            System.out.println("4 - Excluir funcionário");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    listarFuncionarios();
                    break;
                case 3:
                    atualizarFuncionario();
                    break;
                case 4:
                    excluirFuncionario();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
                    break;
            }
        }
    }

    static void cadastrarFuncionario() {

        System.out.println("\n-- Cadastrar Funcionário --");

        Funcionario f = new Funcionario();

        System.out.print("Nome: ");
        f.setNome(sc.nextLine());

        System.out.print("Cargo: ");
        f.setCargo(sc.nextLine());

        System.out.print("Telefone: ");
        f.setTelefone(sc.nextLine());

        try {
            funcionarioService.cadastrarFuncionario(f);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void listarFuncionarios() {

        System.out.println("\n-- Lista de Funcionários --");

        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        System.out.println("-------------------------------------------");
        System.out.printf("%-5s %-20s %-15s %-15s%n",
                "ID", "Nome", "Cargo", "Telefone");
        System.out.println("-------------------------------------------");

        for (Funcionario f : funcionarios) {
            System.out.printf("%-5d %-20s %-15s %-15s%n",
                    f.getIdFuncionario(),
                    f.getNome(),
                    f.getCargo(),
                    f.getTelefone());
        }

        System.out.println("-------------------------------------------");
    }

    static void atualizarFuncionario() {

        listarFuncionarios();

        System.out.print("\nDigite o ID do funcionário a atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Funcionario f = new Funcionario();
        f.setIdFuncionario(id);

        System.out.print("Novo nome: ");
        f.setNome(sc.nextLine());

        System.out.print("Novo cargo: ");
        f.setCargo(sc.nextLine());

        System.out.print("Novo telefone: ");
        f.setTelefone(sc.nextLine());

        try {
            funcionarioService.atualizarFuncionario(f);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void excluirFuncionario() {

        listarFuncionarios();

        System.out.print("\nDigite o ID do funcionário a excluir: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            funcionarioService.excluirFuncionario(id);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ===================== MENU SERVIÇO =====================

    static void menuServico() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n--- SERVIÇOS ---");
            System.out.println("1 - Cadastrar serviço");
            System.out.println("2 - Listar serviços");
            System.out.println("3 - Atualizar serviço");
            System.out.println("4 - Excluir serviço");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarServico();
                    break;
                case 2:
                    listarServicos();
                    break;
                case 3:
                    atualizarServico();
                    break;
                case 4:
                    excluirServico();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
                    break;
            }
        }
    }

    static void cadastrarServico() {

        System.out.println("\n-- Cadastrar Serviço --");

        Servico s = new Servico();

        System.out.print("Nome do serviço: ");
        s.setNomeServico(sc.nextLine());

        System.out.print("Descrição: ");
        s.setDescricao(sc.nextLine());

        System.out.print("Valor (ex: 49.90): ");
        s.setValor(sc.nextDouble());
        sc.nextLine();

        try {
            servicoService.cadastrarServico(s);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void listarServicos() {

        System.out.println("\n-- Lista de Serviços --");

        List<Servico> servicos = servicoService.listarServicos();

        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("%-5s %-20s %-20s %-10s%n",
                "ID", "Nome", "Descrição", "Valor");
        System.out.println("--------------------------------------------------");

        for (Servico s : servicos) {
            System.out.printf("%-5d %-20s %-20s R$ %.2f%n",
                    s.getIdServico(),
                    s.getNomeServico(),
                    s.getDescricao(),
                    s.getValor());
        }

        System.out.println("--------------------------------------------------");
    }

    static void atualizarServico() {

        listarServicos();

        System.out.print("\nDigite o ID do serviço a atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Servico s = new Servico();
        s.setIdServico(id);

        System.out.print("Novo nome: ");
        s.setNomeServico(sc.nextLine());

        System.out.print("Nova descrição: ");
        s.setDescricao(sc.nextLine());

        System.out.print("Novo valor (ex: 49.90): ");
        s.setValor(sc.nextDouble());
        sc.nextLine();

        try {
            servicoService.atualizarServico(s);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void excluirServico() {

        listarServicos();

        System.out.print("\nDigite o ID do serviço a excluir: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            servicoService.excluirServico(id);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ===================== MENU ATENDIMENTO =====================

    static void menuAtendimento() {

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n--- ATENDIMENTOS ---");
            System.out.println("1 - Cadastrar atendimento");
            System.out.println("2 - Listar atendimentos");
            System.out.println("3 - Atualizar atendimento");
            System.out.println("4 - Excluir atendimento");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarAtendimento();
                    break;
                case 2:
                    listarAtendimentos();
                    break;
                case 3:
                    atualizarAtendimento();
                    break;
                case 4:
                    excluirAtendimento();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
                    break;
            }
        }
    }

    static void cadastrarAtendimento() {

        System.out.println("\n-- Cadastrar Atendimento --");

        listarClientes();
        System.out.print("ID do cliente: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        listarFuncionarios();
        System.out.print("ID do funcionário: ");
        int idFuncionario = sc.nextInt();
        sc.nextLine();

        System.out.print("Observação: ");
        String observacao = sc.nextLine();

        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);

        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario(idFuncionario);

        Atendimento atendimento = new Atendimento();
        atendimento.setCliente(cliente);
        atendimento.setFuncionario(funcionario);
        atendimento.setObservacao(observacao);
        atendimento.setDataAtendimento(LocalDateTime.now());

        List<AtendimentoServico> servicos = new ArrayList<>();

        listarServicos();

        boolean adicionarMais = true;

        while (adicionarMais) {

            System.out.print("ID do serviço a adicionar: ");
            int idServico = sc.nextInt();
            sc.nextLine();

            System.out.print("Quantidade: ");
            int quantidade = sc.nextInt();
            sc.nextLine();

            Servico servico = new Servico();
            servico.setIdServico(idServico);

            AtendimentoServico atendimentoServico = new AtendimentoServico();
            atendimentoServico.setServico(servico);
            atendimentoServico.setQuantidade(quantidade);

            servicos.add(atendimentoServico);

            System.out.print("Adicionar outro serviço? (S/N): ");
            String resposta = sc.nextLine();
            adicionarMais = resposta.equalsIgnoreCase("S");
        }

        try {
            atendimentoService.cadastrarAtendimento(atendimento, servicos);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void listarAtendimentos() {

        System.out.println("\n-- Lista de Atendimentos --");

        List<Atendimento> atendimentos = atendimentoService.listarAtendimentos();

        if (atendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento cadastrado.");
            return;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-5s %-12s %-12s %-22s %-20s%n",
                "ID", "ID Cliente", "ID Func.", "Data", "Observação");
        System.out.println("---------------------------------------------------------------");

        for (Atendimento a : atendimentos) {
            System.out.printf("%-5d %-12d %-12d %-22s %-20s%n",
                    a.getIdAtendimento(),
                    a.getCliente().getIdCliente(),
                    a.getFuncionario().getIdFuncionario(),
                    a.getDataAtendimento(),
                    a.getObservacao());
        }

        System.out.println("---------------------------------------------------------------");
    }

    static void atualizarAtendimento() {

        listarAtendimentos();

        System.out.print("\nDigite o ID do atendimento a atualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Novo ID do cliente: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        System.out.print("Novo ID do funcionário: ");
        int idFuncionario = sc.nextInt();
        sc.nextLine();

        System.out.print("Nova observação: ");
        String observacao = sc.nextLine();

        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);

        Funcionario funcionario = new Funcionario();
        funcionario.setIdFuncionario(idFuncionario);

        Atendimento atendimento = new Atendimento();
        atendimento.setIdAtendimento(id);
        atendimento.setCliente(cliente);
        atendimento.setFuncionario(funcionario);
        atendimento.setObservacao(observacao);
        // A data é atualizada para o momento da edição, pois o DAO exige uma data válida.
        atendimento.setDataAtendimento(LocalDateTime.now());

        try {
            atendimentoService.atualizarAtendimento(atendimento);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    static void excluirAtendimento() {

        listarAtendimentos();

        System.out.print("\nDigite o ID do atendimento a excluir: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            atendimentoService.excluirAtendimento(id);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
}