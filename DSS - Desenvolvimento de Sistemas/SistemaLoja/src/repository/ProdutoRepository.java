package repository;

import java.util.ArrayList;
import java.util.List;
import model.Produto;

public class ProdutoRepository {
    private List<Produto> produtos = new ArrayList<>();

    public void adicionar(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> listarTodos() {
        return produtos;
    }
}
