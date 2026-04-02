package model;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int quantidadeEmEstoque;

    public Produto(int id, String nome, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidade;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQuantidadeEmEstoque() { return quantidadeEmEstoque; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Preço: R$" + preco + " | Qtd: " + quantidadeEmEstoque;
    }
}
