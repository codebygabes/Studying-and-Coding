package br.spring.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.spring.model.Emprestimo;

@Repository
public class EmprestimoRepository {
    private static List<Emprestimo> emprestimos = new ArrayList<>();

    public Emprestimo salvar(Emprestimo emprestimo) {
        if (emprestimo.getId() == 0) {
            int novoId = 0;
            for (Emprestimo e : emprestimos) {
                if (e.getId() > novoId) {
                    novoId = e.getId();
                }
            }
            novoId++;
            emprestimo.setId(novoId);
        }
        emprestimos.add(emprestimo);
        return emprestimo;
    }

    public Emprestimo buscarPorId(int id) {
        for (Emprestimo e : emprestimos) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public List<Emprestimo> buscarPorUsuario(int usuarioId) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getUsuarioId() == usuarioId) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    public List<Emprestimo> buscarEmprestimosAtivos(int usuarioId) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getUsuarioId() == usuarioId && e.isAtivo()) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos);
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.isAtivo()) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    public void atualizar(Emprestimo emprestimo) {
        for (int i = 0; i < emprestimos.size(); i++) {
            if (emprestimos.get(i).getId() == emprestimo.getId()) {
                emprestimos.set(i, emprestimo);
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < emprestimos.size(); i++) {
            if (emprestimos.get(i).getId() == id) {
                emprestimos.remove(i);
                break;
            }
        }
    }

    public List<Emprestimo> buscarEmprestimosAtivosPorLivro(int livroId) {
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getLivroId() == livroId && e.isAtivo()) {
                resultado.add(e);
            }
        }
        return resultado;
    }
}
