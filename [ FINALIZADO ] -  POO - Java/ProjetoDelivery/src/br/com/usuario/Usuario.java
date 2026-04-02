package br.com.usuario;

import br.com.restaurante.Pedido;

public class Usuario {
	private String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

    public Pedido fazerPedido() {
        return new Pedido(this);
    }

    public String getNome() {
        return nome;
    }
}
