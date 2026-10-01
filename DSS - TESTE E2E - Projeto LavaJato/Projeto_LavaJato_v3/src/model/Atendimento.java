package model;

import java.time.LocalDateTime;

public class Atendimento {

	private int idAtendimento;
    private Cliente cliente;
    private Funcionario funcionario;
    private LocalDateTime dataAtendimento;
    private String observacao;
    
    
    //getter e setter
	public int getIdAtendimento() {
		return idAtendimento;
	}
	public void setIdAtendimento(int idAtendimento) {
		this.idAtendimento = idAtendimento;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public LocalDateTime getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(LocalDateTime dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
    
    
	
}
