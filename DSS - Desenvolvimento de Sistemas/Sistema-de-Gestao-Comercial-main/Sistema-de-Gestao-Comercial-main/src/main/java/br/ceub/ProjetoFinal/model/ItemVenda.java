package br.ceub.ProjetoFinal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_venda")
public class ItemVenda {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @ManyToOne
    @JoinColumn(name="produto_id", nullable = false)
    private Produto produto;

    @Column(name="quantidade", nullable = false)
    private Integer quantidade;

    @Column(name="preco_unitario", nullable = false)
    private Double precoUnitario;
    
    @ManyToOne
    @JoinColumn(name="venda_id", nullable = false)
    private Venda venda;

    public ItemVenda() {
        super();
    }

    public ItemVenda(Integer id, Produto produto, Integer quantidade, Double precoUnitario) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
    
    
    

}
