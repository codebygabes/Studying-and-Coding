package br.com.gabes.animal;

import br.com.gabes.interfaces.Animal;

public class AnimalFactory {

		public Animal gerarAnimal(String tipo) {
		
			if (tipo == "Gato") {
				return new Gato();
			}else if (tipo == "Cachorro") {
				return new Cachorro();
			}else {
				return null;
			}
		}
}
