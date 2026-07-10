package br.ceub.ProjetoFinal.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "venda")
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="data", nullable = false)
	private LocalDate data;

	@Column(name="valorTotal", nullable = false)
	private Double valorTotal;

    @ManyToOne
    @JoinColumn(name="clienteId", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name="usuarioId", nullable = false)
    private Usuario usuario;
    
    public Venda() {
        super();
    }

    public Venda(Integer id, LocalDate data, Double valorTotal, Cliente cliente, Usuario usuario) {
        this.id = id;
        this.data = data;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setClienteId(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuarioId(Usuario usuario) {
        this.usuario = usuario;
    }

	public Object getQuantidade() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setQuantidade(Object quantidade) {
		// TODO Auto-generated method stub
		
	}
    
    
    

}
