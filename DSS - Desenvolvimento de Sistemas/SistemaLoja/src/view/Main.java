package view;

import model.Produto;
import repository.ProdutoRepository;
import service.ProdutoService;

public class Main {
    public static void main(String[] args) {
        ProdutoRepository repo = new ProdutoRepository();
        ProdutoService service = new ProdutoService(repo);

        // Cadastro de exemplo
        service.cadastrarProduto(new Produto(1, "Teclado", 150.0, 10));
        service.cadastrarProduto(new Produto(2, "Mouse", 80.0, 20));
        service.cadastrarProduto(new Produto(3, "Monitor", 900.0, 5));

        System.out.println("--- Produtos Ordenados ---");
        for (Produto p : service.listarOrdenado()) {
            System.out.println(p);
        }

        System.out.println("\nValor Total em Estoque: R$ " + service.calcularValorTotalEstoque());
    }
}