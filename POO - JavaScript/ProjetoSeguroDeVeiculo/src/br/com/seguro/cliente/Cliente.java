package br.com.seguro.cliente;

public class Cliente {

	private String nome;

	public Cliente(String nome) {
		super();
		this.nome = nome;
	}
	
	public double CalcularValorSeguro(double valor) {
		return valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
