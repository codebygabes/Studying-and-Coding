package br.com.seguro.cliente;

public class ClienteAutoRisco extends Cliente {

	public ClienteAutoRisco(String nome) {
		super(nome);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double CalcularValorSeguro(double valor) {
		// TODO Auto-generated method stub
		return super.CalcularValorSeguro(valor)+500;
	}

}
