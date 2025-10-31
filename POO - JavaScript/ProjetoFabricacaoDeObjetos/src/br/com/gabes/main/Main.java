package br.com.gabes.main;

import br.com.gabes.animal.AnimalFactory;
import br.com.gabes.interfaces.Animal;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AnimalFactory objAnimalFactory = new AnimalFactory();
		Animal objGato = objAnimalFactory.gerarAnimal("Gato");
		objGato.emitirSom();
	}

}
