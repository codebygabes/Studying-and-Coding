package br.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.spring.model.Livro;
import br.spring.service.LivroService;

@Component
public class LivroController {
    @Autowired
    private LivroService livroService;

    public Livro cadastrar(Livro livro) {
        return livroService.cadastrar(livro);
    }

    public Livro buscarPorId(int id) {
        return livroService.buscarPorId(id);
    }

    public List<Livro> buscarPorNome(String nome) {
        return livroService.buscarPorNome(nome);
    }

    public List<Livro> listarTodos() {
        return livroService.listarTodos();
    }

    public void alterar(Livro livro) {
        livroService.alterar(livro);
    }

    public void remover(int id) {
        livroService.remover(id);
    }
}