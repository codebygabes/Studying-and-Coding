package br.com.fiat.main;

import br.com.fiat.veiculo.Carro;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Vamos Instanciar um Objeto Carro
		Carro objFerrari = new Carro("Ferrari","488 Spider",2023);
		objFerrari.exibirInformacoesCarro();
		
		// Tirar o Fiat da Forma
		Carro objFiat = new Carro("Fiat","Uno",2007);
		objFiat.exibirInformacoesCarro();
		
	}

}
