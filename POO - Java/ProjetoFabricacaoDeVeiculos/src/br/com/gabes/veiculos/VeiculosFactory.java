package br.com.gabes.veiculos;

import br.com.gabes.interfaces.Veiculos;

public class VeiculosFactory {
	
	public Veiculos gerarVeiculo(String tipo) {
		
			if (tipo == "Carro") {
				return new Carro();
			} else if (tipo == "Moto") {
				return new Moto();
			} else if (tipo == "Bicicleta") {
				return new Bicicleta();
			} else {
				return null;
			}
	}
}
