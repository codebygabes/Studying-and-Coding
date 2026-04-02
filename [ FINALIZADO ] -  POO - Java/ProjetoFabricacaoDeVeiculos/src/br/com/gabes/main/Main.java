package br.com.gabes.main;

import br.com.gabes.interfaces.Veiculos;
import br.com.gabes.veiculos.VeiculosFactory;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VeiculosFactory objVeiculosFactory = new VeiculosFactory();
		Veiculos objCarro = objVeiculosFactory.gerarVeiculo("Moto");
		objCarro.emMovimento();
	}

}
