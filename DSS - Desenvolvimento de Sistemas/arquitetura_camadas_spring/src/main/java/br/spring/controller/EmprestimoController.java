package br.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.spring.model.Emprestimo;
import br.spring.service.EmprestimoService;

@Component
public class EmprestimoController {
    @Autowired
    private EmprestimoService emprestimoService;

    public Emprestimo realizarEmprestimo(int usuarioId, int livroId, int diasEmprestimo) {
        return emprestimoService.realizarEmprestimo(usuarioId, livroId, diasEmprestimo);
    }

    public Emprestimo buscarPorId(int id) {
        return emprestimoService.buscarPorId(id);
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoService.listarTodosEmprestimos();
    }

    public void devolverLivro(int emprestimoId) {
        emprestimoService.devolverLivro(emprestimoId);
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoService.listarEmprestimosAtivos();
    }

    public List<Emprestimo> buscarEmprestimosDoUsuario(int usuarioId) {
        return emprestimoService.buscarEmprestimosDoUsuario(usuarioId);
    }

    public List<Emprestimo> buscarEmprestimosAtivosDoUsuario(int usuarioId) {
        return emprestimoService.buscarEmprestimosAtivosDoUsuario(usuarioId);
    }

    public boolean verificarAtraso(int emprestimoId) {
        return emprestimoService.verificarAtraso(emprestimoId);
    }
}