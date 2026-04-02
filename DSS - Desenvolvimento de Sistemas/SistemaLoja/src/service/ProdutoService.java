package service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Produto;
import repository.ProdutoRepository;

public class ProdutoService {
    private ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void cadastrarProduto(Produto p) {
        repository.adicionar(p);
    }

    public List<Produto> listarOrdenado() {
        List<Produto> lista = repository.listarTodos();
        // Ordena por nome
        lista.sort(Comparator.comparing(Produto::getNome));
        return lista;
    }

    public double calcularValorTotalEstoque() {
        double total = 0;
        for (Produto p : repository.listarTodos()) {
            total += p.getPreco() * p.getQuantidadeEmEstoque();
        }
        return total;
    }
}
