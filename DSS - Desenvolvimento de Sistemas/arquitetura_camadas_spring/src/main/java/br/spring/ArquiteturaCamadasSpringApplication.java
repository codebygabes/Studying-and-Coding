package br.spring;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import br.spring.controller.EmprestimoController;
import br.spring.controller.LivroController;
import br.spring.controller.UsuarioController;
import br.spring.model.Emprestimo;
import br.spring.model.Livro;
import br.spring.model.Usuario;

@SpringBootApplication
public class ArquiteturaCamadasSpringApplication {

    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private LivroController livroController;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private EmprestimoController emprestimoController;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ArquiteturaCamadasSpringApplication.class, args);
        context.getBean(ArquiteturaCamadasSpringApplication.class).runApp();
        context.close();
    }

    public void runApp() {
        System.out.println("===== SISTEMA DE GERENCIAMENTO DE BIBLIOTECA =====\n");

        while (true) {
            exibirMenuPrincipal();
            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> menuLivros();
                case 2 -> menuUsuarios();
                case 3 -> menuEmprestimos();
                case 0 -> {
                    System.out.println("Saindo do sistema...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Livros");
        System.out.println("2. Gerenciar Usuários");
        System.out.println("3. Gerenciar Empréstimos");
        System.out.println("0. Sair");
    }

    private void menuLivros() {
        while (true) {
            System.out.println("\n=== MENU LIVROS ===");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Buscar Livro por ID");
            System.out.println("3. Buscar Livros por Nome");
            System.out.println("4. Listar Todos os Livros");
            System.out.println("5. Alterar Livro");
            System.out.println("6. Remover Livro");
            System.out.println("0. Voltar");

            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    break;
                case 2:
                    buscarLivroPorId();
                    break;
                case 3:
                    buscarLivrosPorNome();
                    break;
                case 4:
                    listarTodosLivros();
                    break;
                case 5:
                    alterarLivro();
                    break;
                case 6:
                    removerLivro();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void menuUsuarios() {
        while (true) {
            System.out.println("\n=== MENU USUÁRIOS ===");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Buscar Usuário por ID");
            System.out.println("3. Buscar Usuário por CPF");
            System.out.println("4. Buscar Usuário por Email");
            System.out.println("5. Buscar Usuários por Nome");
            System.out.println("6. Listar Todos os Usuários");
            System.out.println("7. Alterar Usuário");
            System.out.println("8. Remover Usuário");
            System.out.println("0. Voltar");

            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    buscarUsuarioPorId();
                    break;
                case 3:
                    buscarUsuarioPorCpf();
                    break;
                case 4:
                    buscarUsuarioPorEmail();
                    break;
                case 5:
                    buscarUsuariosPorNome();
                    break;
                case 6:
                    listarTodosUsuarios();
                    break;
                case 7:
                    alterarUsuario();
                    break;
                case 8:
                    removerUsuario();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void menuEmprestimos() {
        while (true) {
            System.out.println("\n=== MENU EMPRÉSTIMOS ===");
            System.out.println("1. Realizar Empréstimo");
            System.out.println("2. Devolver Livro");
            System.out.println("3. Buscar Empréstimo por ID");
            System.out.println("4. Listar Todos os Empréstimos");
            System.out.println("5. Listar Empréstimos Ativos");
            System.out.println("6. Buscar Empréstimos do Usuário");
            System.out.println("7. Verificar Atraso");
            System.out.println("0. Voltar");

            int opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    realizarEmprestimo();
                    break;
                case 2:
                    devolverLivro();
                    break;
                case 3:
                    buscarEmprestimoPorId();
                    break;
                case 4:
                    listarTodosEmprestimos();
                    break;
                case 5:
                    listarEmprestimosAtivos();
                    break;
                case 6:
                    buscarEmprestimosDoUsuario();
                    break;
                case 7:
                    verificarAtraso();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private void cadastrarLivro() {
        try {
            System.out.println("\n--- Cadastrar Livro ---");
            String nome = lerString("Nome: ");
            String autor = lerString("Autor: ");
            int edicao = lerInteiro("Edição: ");
            int quantidade = lerInteiro("Quantidade: ");

            Livro livro = new Livro(0, nome, autor, edicao, quantidade);
            Livro salvo = livroController.cadastrar(livro);
            System.out.println("Livro cadastrado com sucesso! ID: " + salvo.getId());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarLivroPorId() {
        try {
            int id = lerInteiro("ID do livro: ");
            Livro livro = livroController.buscarPorId(id);
            if (livro != null) {
                System.out.println(livro);
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarLivrosPorNome() {
        try {
            String nome = lerString("Nome do livro: ");
            List<Livro> livros = livroController.buscarPorNome(nome);
            if (livros.isEmpty()) {
                System.out.println("Nenhum livro encontrado.");
            } else {
                livros.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTodosLivros() {
        List<Livro> livros = livroController.listarTodos();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void alterarLivro() {
        try {
            int id = lerInteiro("ID do livro: ");
            Livro existente = livroController.buscarPorId(id);
            if (existente == null) {
                System.out.println("Livro não encontrado.");
                return;
            }

            System.out.println("Dados atuais: " + existente);
            String nome = lerString("Novo nome (ou Enter para manter): ");
            if (!nome.isEmpty()) existente.setNome(nome);

            String autor = lerString("Novo autor (ou Enter para manter): ");
            if (!autor.isEmpty()) existente.setAutor(autor);

            String edicaoStr = lerString("Nova edição (ou Enter para manter): ");
            if (!edicaoStr.isEmpty()) existente.setEdicao(Integer.parseInt(edicaoStr));

            String qtdStr = lerString("Nova quantidade (ou Enter para manter): ");
            if (!qtdStr.isEmpty()) existente.setQuantidade(Integer.parseInt(qtdStr));

            livroController.alterar(existente);
            System.out.println("Livro alterado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerLivro() {
        try {
            int id = lerInteiro("ID do livro: ");
            livroController.remover(id);
            System.out.println("Livro removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cadastrarUsuario() {
        try {
            System.out.println("\n--- Cadastrar Usuário ---");
            String nome = lerString("Nome: ");
            String email = lerString("Email: ");
            String cpf = lerString("CPF: ");
            String telefone = lerString("Telefone: ");

            Usuario usuario = new Usuario(0, nome, email, cpf, telefone);
            Usuario salvo = usuarioController.cadastrar(usuario);
            System.out.println("Usuário cadastrado com sucesso! ID: " + salvo.getId());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarUsuarioPorId() {
        try {
            int id = lerInteiro("ID do usuário: ");
            Usuario usuario = usuarioController.buscarPorId(id);
            if (usuario != null) {
                System.out.println(usuario);
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarUsuarioPorCpf() {
        try {
            String cpf = lerString("CPF do usuário: ");
            Usuario usuario = usuarioController.buscarPorCpf(cpf);
            if (usuario != null) {
                System.out.println(usuario);
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarUsuarioPorEmail() {
        try {
            String email = lerString("Email do usuário: ");
            Usuario usuario = usuarioController.buscarPorEmail(email);
            if (usuario != null) {
                System.out.println(usuario);
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarUsuariosPorNome() {
        try {
            String nome = lerString("Nome do usuário: ");
            List<Usuario> usuarios = usuarioController.buscarPorNome(nome);
            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário encontrado.");
            } else {
                usuarios.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioController.listarTodos();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            usuarios.forEach(System.out::println);
        }
    }

    private void alterarUsuario() {
        try {
            int id = lerInteiro("ID do usuário: ");
            Usuario existente = usuarioController.buscarPorId(id);
            if (existente == null) {
                System.out.println("Usuário não encontrado.");
                return;
            }

            System.out.println("Dados atuais: " + existente);
            String nome = lerString("Novo nome (ou Enter para manter): ");
            if (!nome.isEmpty()) existente.setNome(nome);

            String email = lerString("Novo email (ou Enter para manter): ");
            if (!email.isEmpty()) existente.setEmail(email);

            String cpf = lerString("Novo CPF (ou Enter para manter): ");
            if (!cpf.isEmpty()) existente.setCpf(cpf);

            String telefone = lerString("Novo telefone (ou Enter para manter): ");
            if (!telefone.isEmpty()) existente.setTelefone(telefone);

            usuarioController.alterar(existente);
            System.out.println("Usuário alterado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerUsuario() {
        try {
            int id = lerInteiro("ID do usuário: ");
            usuarioController.remover(id);
            System.out.println("Usuário removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void realizarEmprestimo() {
        try {
            System.out.println("\n--- Realizar Empréstimo ---");
            int usuarioId = lerInteiro("ID do usuário: ");
            int livroId = lerInteiro("ID do livro: ");
            int dias = lerInteiro("Dias de empréstimo: ");

            Emprestimo emprestimo = emprestimoController.realizarEmprestimo(usuarioId, livroId, dias);
            System.out.println("Empréstimo realizado com sucesso! ID: " + emprestimo.getId());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void devolverLivro() {
        try {
            int id = lerInteiro("ID do empréstimo: ");
            emprestimoController.devolverLivro(id);
            System.out.println("Livro devolvido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarEmprestimoPorId() {
        try {
            int id = lerInteiro("ID do empréstimo: ");
            Emprestimo emprestimo = emprestimoController.buscarPorId(id);
            if (emprestimo != null) {
                System.out.println(emprestimo);
            } else {
                System.out.println("Empréstimo não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarTodosEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoController.listarTodos();
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo cadastrado.");
        } else {
            emprestimos.forEach(System.out::println);
        }
    }

    private void listarEmprestimosAtivos() {
        List<Emprestimo> emprestimos = emprestimoController.listarEmprestimosAtivos();
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo ativo.");
        } else {
            emprestimos.forEach(System.out::println);
        }
    }

    private void buscarEmprestimosDoUsuario() {
        try {
            int id = lerInteiro("ID do usuário: ");
            List<Emprestimo> emprestimos = emprestimoController.buscarEmprestimosDoUsuario(id);
            if (emprestimos.isEmpty()) {
                System.out.println("Nenhum empréstimo encontrado para este usuário.");
            } else {
                emprestimos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void verificarAtraso() {
        try {
            int id = lerInteiro("ID do empréstimo: ");
            boolean atrasado = emprestimoController.verificarAtraso(id);
            if (atrasado) {
                System.out.println("O empréstimo está em atraso.");
            } else {
                System.out.println("O empréstimo não está em atraso.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

