package br.spring.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.spring.model.Livro;

@Repository
public class LivroRepository__ {
    private static List<Livro> livros = new ArrayList<>();

    public Livro salvar(Livro livro) {
        if (livro.getId() == 0) {
            // Novo livro, gerar ID
            int novoId = 0;
            for (Livro l : livros) {
                if (l.getId() > novoId) {
                    novoId = l.getId();
                }
            }
            novoId++;
            livro.setId(novoId);
        }
        livros.add(livro);
        return livro;
    }

    public Livro buscarPorId(int id) {
        for (Livro l : livros) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    public List<Livro> buscarPorNome(String nome) {
        List<Livro> resultado = new ArrayList<>();
        String nomeLower = nome.toLowerCase();
        for (Livro l : livros) {
            if (l.getNome().toLowerCase().contains(nomeLower)) {
                resultado.add(l);
            }
        }
        return resultado;
    }

    public List<Livro> listarTodos() {
        return new ArrayList<>(livros);
    }

    public void atualizar(Livro livro) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId() == livro.getId()) {
                livros.set(i, livro);
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId() == id) {
                livros.remove(i);
                break;
            }
        }
    }
}
