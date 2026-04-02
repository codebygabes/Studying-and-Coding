package br.com.restaurante;

import java.util.ArrayList;
import java.util.List;

import br.com.interfaces.Entregavel;
import br.com.status.StatusEntrega;
import br.com.usuario.Usuario;

public class Pedido implements Entregavel {
	private Usuario usuario;
    private List<Item> itens;
    private String status;

    public Pedido(Usuario usuario) {
    	this.usuario = usuario;
        this.itens = new ArrayList<>();
        this.status = StatusEntrega.PENDENTE; // Referência à constante
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }
    
    public void entregar() {
        status = StatusEntrega.ENTREGUE;
        System.out.println("PEDIDO ENTREGUE!");
    }
    
    public String getStatus() {
        return status;
    }

    public List<Item> getItens() {
        return itens;
    }

    public double calcularTotal() {
        double total = 0;
        for(Item item : itens) {
            total += item.getPreco();
        }
        return total;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
}
