package br.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="livros")
public class Livro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nome", length = 100, nullable = false)
    private String nome;
	
	@Column(name = "autor", length = 100, nullable = false)
    private String autor;
	
	@Column(name = "edicao", nullable = false)
    private int edicao;
    
	@Column(name = "quantidade", nullable = false)
    private int quantidade;

    public Livro() {
    }

    public Livro(int id, String nome, String autor, int edicao, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.autor = autor;
        this.edicao = edicao;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", autor='" + edicao + '\'' +
                ", edicao=" + edicao +
                ", quantidade=" + quantidade +
                '}';
    }
}
