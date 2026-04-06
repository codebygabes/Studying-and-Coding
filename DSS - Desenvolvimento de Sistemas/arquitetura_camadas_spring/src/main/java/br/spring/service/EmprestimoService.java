package br.spring.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.spring.model.Emprestimo;
import br.spring.model.Livro;
import br.spring.model.Usuario;
import br.spring.repository.EmprestimoRepository;
import br.spring.repository.LivroRepository;
import br.spring.repository.LivroRepository__;
import br.spring.repository.UsuarioRepository;

@Service
public class EmprestimoService {
    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LivroRepository livroRepository;

    public Emprestimo realizarEmprestimo(int usuarioId, int livroId, int diasEmprestimo) {
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Livro livro = livroRepository.findById(livroId).orElse(null);
        if (livro == null) {
            throw new IllegalArgumentException("Livro não encontrado");
        }

        // Verificar se há exemplares disponíveis
        List<Emprestimo> emprestimosAtivos = emprestimoRepository.buscarEmprestimosAtivosPorLivro(livroId);
        if (emprestimosAtivos.size() >= livro.getQuantidade()) {
            throw new IllegalArgumentException("Não há exemplares disponíveis deste livro");
        }

        // Verificar se usuário já tem empréstimos ativos
        List<Emprestimo> emprestimosUsuario = emprestimoRepository.buscarEmprestimosAtivos(usuarioId);
        if (emprestimosUsuario.size() >= 3) { // Limite de 3 empréstimos por usuário
            throw new IllegalArgumentException("Usuário já possui o limite máximo de empréstimos ativos");
        }

        LocalDate hoje = LocalDate.now();
        LocalDate dataDevolucaoPrevista = hoje.plusDays(diasEmprestimo);

        Emprestimo emprestimo = new Emprestimo(0, usuarioId, livroId, hoje, dataDevolucaoPrevista);
        return emprestimoRepository.salvar(emprestimo);
    }

    public void devolverLivro(int emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.buscarPorId(emprestimoId);
        if (emprestimo == null) {
            throw new IllegalArgumentException("Empréstimo não encontrado");
        }
        if (!emprestimo.isAtivo()) {
            throw new IllegalArgumentException("Empréstimo já foi devolvido");
        }

        emprestimo.setDataDevoluçaoReal(LocalDate.now());
        emprestimo.setAtivo(false);
        emprestimoRepository.atualizar(emprestimo);
    }

    public List<Emprestimo> buscarEmprestimosDoUsuario(int usuarioId) {
        if (usuarioRepository.buscarPorId(usuarioId) == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return emprestimoRepository.buscarPorUsuario(usuarioId);
    }

    public List<Emprestimo> buscarEmprestimosAtivosDoUsuario(int usuarioId) {
        if (usuarioRepository.buscarPorId(usuarioId) == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return emprestimoRepository.buscarEmprestimosAtivos(usuarioId);
    }

    public List<Emprestimo> listarTodosEmprestimos() {
        return emprestimoRepository.listarTodos();
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoRepository.listarEmprestimosAtivos();
    }

    public Emprestimo buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return emprestimoRepository.buscarPorId(id);
    }

    public boolean verificarAtraso(int emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.buscarPorId(emprestimoId);
        if (emprestimo == null || !emprestimo.isAtivo()) {
            return false;
        }
        return LocalDate.now().isAfter(emprestimo.getDataDevolucaoPrevista());
    }
}
