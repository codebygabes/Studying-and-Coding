package br.com.fiat.veiculo;

public class Carro {
	
	// Atributos - Caracteristicas
	private String marca;
	private String modelo;
	private int ano;
	
	public Carro(String marca, String modelo, int ano) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
	}

	public void exibirInformacoesCarro() {
		System.out.println(modelo+" : Modelo do Carro!");
		System.out.println(marca+" : Marca do Carro!");
		System.out.println(ano+" : Ano de Lançamento!");
		System.out.println("---- ---- ---- ---- ---- ----");
	}

}
