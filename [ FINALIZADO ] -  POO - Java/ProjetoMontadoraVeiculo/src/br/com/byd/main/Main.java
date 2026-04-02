package br.com.byd.main;
import br.com.byd.veiculo.Carro;
import br.com.byd.veiculo.Moto;

public class Main {

	public static void main(String[] args) {
		Carro objCarro = new Carro(2025, "Vermelho", "Seal", 4, 5, "Eletrico");
		objCarro.mover();
		objCarro.parar();
		objCarro.abrirAirBagColuna();

		Moto objMoto = new Moto (2025, "Azul", "CB1000", "Sim");
		objMoto.repouso();
	}

}
