package br.com.byd.veiculo;

public class Veiculo {

	//	Atributos ---
	private int ano;
	private String cor;
	private String modelo;
	
	//	Construtor ---
	public Veiculo(int ano, String cor, String modelo) {
		super();
		this.ano = ano;
		this.cor = cor;
		this.modelo = modelo;
	}

	//	Metados ---
	public void mover() {
		System.out.println("Veiculo está Movendo!");
	}
	
	public void parar() {
		System.out.println("Veiculo está Parando!");
		System.out.println(" ======== ======= ======= =======");
	}
}
