package br.com.byd.veiculo;

public class Moto extends Veiculo {

	private String peDeApoio;
	
	public Moto(int ano, String cor, String modelo, String peDeApoio) {
		super(ano, cor, modelo);
		this.peDeApoio = peDeApoio;
	}

	public void repouso () {
		System.out.println ("Tirando o Apoio para deixar a moto em Repouso! ");
		System.out.println(" ======== ======= ======= =======");
	}
}
