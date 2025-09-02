package br.com.byd.veiculo;

public class Carro extends Veiculo {

	private int qtdRodas;
	private int qtdBancos;
	private String motor;
	
	public Carro(int ano, String cor, String modelo, int qtdRodas, int qtdBancos, String motor) {
		super(ano, cor, modelo);
		this.qtdRodas = qtdRodas;
		this.qtdBancos = qtdBancos;
		this.motor = motor;
	}
	
	public void abrirAirBagColuna() {
		System.out.println ("Abrindo AirBag");
		System.out.println(" ======== ======= ======= =======");
	}
}
