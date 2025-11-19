package br.com.main;

import br.com.restaurante.Bebida;
import br.com.restaurante.Comida;
import br.com.restaurante.Item;
import br.com.restaurante.Pedido;
import br.com.restaurante.Sobremesa;
import br.com.usuario.Usuario;

public class Main {

	public static void main(String[] args) {
		Usuario usuario = new Usuario("Naruto");
        Pedido pedido = usuario.fazerPedido();

        pedido.adicionarItem(new Comida("Hambúrguer - Big Mac", 30.00));
        pedido.adicionarItem(new Comida("Batata-Frita - Média", 5.00));
        
        pedido.adicionarItem(new Bebida("Refrigerante - Coca-Cola: Zero", 5.00));
        
        pedido.adicionarItem(new Sobremesa("Sobremesa - Sandei", 7.00));
        
        System.out.println("ITENS DO PEDIDO DO CLIENTE: " +pedido.getUsuario().getNome());
        
        System.out.println("");
        
        for(Item item : pedido.getItens()) {
            System.out.println(item.getNome() + " - R$ " + item.getPreco());
        }
        
        System.out.println("");

        System.out.printf("TOTAL: R$ %.2f\n", pedido.calcularTotal());
        System.out.println("Status Antes da Entrega: " + pedido.getStatus());

        pedido.entregar();
        System.out.println("Status Depois da Entrega: " + pedido.getStatus());
    }

}
