package br.com.itau.conta;

public class ContaBancaria {
	
	private String titular;
	private double saldo;
	private String nuConta;
	private String data;
	
	public ContaBancaria(String titular, double saldo, String nuConta, String data) {
		super();
		this.titular = titular;
		this.saldo = saldo;
		this.nuConta = nuConta;
		this.data = data;
	}
	
	public void verificarSaldo() {
		System.out.println("Titular : "+titular);
		System.out.println("Saldo : "+saldo);
		System.out.println("Numero Conta : "+nuConta);
		System.out.println("Data : "+data);
		System.out.println(" ");
	}
	
	public void depositar(double valor) {
		saldo+=valor;
		System.out.println("Saldo Pos Deposito : "+saldo);
	}
	
	public void sacar(double valor) {
		saldo-=valor;
		System.out.println("Valor Pos Saque : "+saldo);
		System.out.println(" ");
	}
}
