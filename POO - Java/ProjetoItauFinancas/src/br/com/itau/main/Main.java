package br.com.itau.main;

import br.com.itau.conta.ContaBancaria;

public class Main {

	public static void main(String[] args) {
		
		// Vamos Instanciar um Objeto Animal
		ContaBancaria objConta = new ContaBancaria("Joao", 1200, "11L09", "11/09/2007");
		objConta.verificarSaldo();
		objConta.depositar(700);
		objConta.sacar(400);
  
	}

}
