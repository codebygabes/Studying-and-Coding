package br.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.spring.model.Livro;
import br.spring.repository.LivroRepository;
import br.spring.repository.LivroRepository__;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    public Livro cadastrar(Livro livro) {
        if (livro == null || livro.getNome() == null || livro.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do livro é obrigatório");
        }
        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("Autor do livro é obrigatório");
        }
        if (livro.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        return livroRepository.save(livro);
    }

    public Livro buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return livroRepository.findById(id).orElse(null);
    }

    public List<Livro> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome para busca é obrigatório");
        }
        return livroRepository.findLivroByNome(nome);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public void alterar(Livro livro) {
        if (livro == null || livro.getId() <= 0) {
            throw new IllegalArgumentException("Livro inválido para alteração");
        }
        if (livro.getNome() == null || livro.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do livro é obrigatório");
        }
        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("Autor do livro é obrigatório");
        }
        if (livro.getQuantidade() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        livroRepository.save(livro);
    }

    public void remover(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        Livro livro = livroRepository.findById(id).orElse(null);
        if(livro != null)
        livroRepository.delete(livro);
    }
}
