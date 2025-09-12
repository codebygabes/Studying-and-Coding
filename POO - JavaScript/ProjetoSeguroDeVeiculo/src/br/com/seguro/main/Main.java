package br.com.seguro.main;

import br.com.seguro.cliente.Cliente;
import br.com.seguro.cliente.ClienteAutoRisco;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Cliente objCliente = new Cliente("Gabes");
		double valorPadrao = 2000;
		
		System.out.println("Nome Cliente : "+objCliente.getNome());
		System.out.println("Valor Seguro : "+objCliente.CalcularValorSeguro(valorPadrao));
		
		System.out.println("==== ==== ==== ==== ==== ====");
		
		ClienteAutoRisco objClienteAutoRisco = new ClienteAutoRisco("Maria");
		
		System.out.println("Valor Seguro : "+objClienteAutoRisco.CalcularValorSeguro(valorPadrao));
		System.out.println("Nome Cliente : "+ objClienteAutoRisco.getNome());
	}

}
