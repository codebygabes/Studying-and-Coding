package br.com.petshop;

import br.com.petshop.animal.Animal;

public class Main {

	public static void main(String[] args) {
	
		// Vamos Instanciar um Objeto Animal
		Animal objPet_C = new Animal("Cachorro", "Border Collie", "Femêa", 2);
		objPet_C.exibirAcoes();
		objPet_C.exibirInformacoesAnimal();
		
		Animal objPet_G = new Animal("Gato", "Sphynx", "Macho", 3);
		objPet_G.exibirAcoes();
		objPet_G.exibirInformacoesAnimal();

	}

}
