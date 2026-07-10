package br.ceub.ProjetoFinal.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ceub.ProjetoFinal.model.ItemVenda;
import br.ceub.ProjetoFinal.service.ItemVendaService;

// @RestController indica que esta classe é um controlador REST:
// os métodos retornam dados diretamente no corpo da resposta HTTP (JSON).
// @RequestMapping define o caminho base "/api/itensvenda" para todos os endpoints desta classe.
// ItemVenda representa cada linha de produto dentro de uma venda (produto, quantidade, preço unitário).
@RestController
@RequestMapping("/api/itensvenda")
public class ItemVendaController {

    // @Autowired injeta automaticamente a dependência do ItemVendaService,
    // permitindo o uso dos métodos de negócio sem precisar instanciar manualmente.
    @Autowired
    private ItemVendaService itemVendaService;

    // Endpoint para CRIAR um novo item de venda.
    // @PostMapping mapeia requisições HTTP POST para este método.
    // @RequestBody converte o JSON recebido no corpo da requisição em um objeto ItemVenda.
    // Salva o item no banco de dados e retorna o objeto salvo com status HTTP 200 (OK).
    @PostMapping
    public ResponseEntity<ItemVenda> createItemVenda(@RequestBody ItemVenda itemVenda) {
        ItemVenda savedItemVenda = itemVendaService.save(itemVenda);
        return ResponseEntity.ok(savedItemVenda);
    }

    // Endpoint para LISTAR todos os itens de venda cadastrados.
    // @GetMapping sem parâmetro mapeia GET para /api/itensvenda.
    // Retorna uma lista completa de itens de venda com status HTTP 200 (OK).
    @GetMapping
    public ResponseEntity<List<ItemVenda>> getAllItemVenda() {
        List<ItemVenda> ItemVendas = itemVendaService.findAll();
        return ResponseEntity.ok(ItemVendas);
    }

    // Endpoint para BUSCAR um item de venda específico pelo seu ID.
    // @GetMapping("/{id}") mapeia GET com ID na URL, ex: GET /api/itensvenda/2
    // @PathVariable extrai o valor do {id} diretamente da URL.
    // Se encontrado, retorna 200 (OK) com o item; caso contrário, retorna 404 (Not Found).
    @GetMapping("/{id}")
    public ResponseEntity<ItemVenda> getItemVendaById(@PathVariable Integer id) {
        Optional<ItemVenda> ItemVenda = itemVendaService.findById(id);
        return ItemVenda.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para PESQUISAR itens de venda pelo nome do produto.
    // @GetMapping("/search") mapeia GET para /api/itensvenda/search
    // @RequestParam extrai o parâmetro "nome" da query string, ex: ?nome=Notebook
    // Retorna uma lista de itens cujo nome do produto corresponde ao critério de busca.
    @GetMapping("/search")
    public ResponseEntity<List<ItemVenda>> searchItemVendas(@RequestParam String nome) {
        List<ItemVenda> ItemVendas = itemVendaService.findByNome(nome);
        return ResponseEntity.ok(ItemVendas);
    }

    // Endpoint para ATUALIZAR completamente um item de venda existente (atualização total via PUT).
    // @PutMapping("/{id}") mapeia requisições HTTP PUT com ID na URL.
    // O PUT substitui todos os campos do objeto — diferente do PATCH que é parcial.
    // Atualiza o produto associado, a quantidade e o preço unitário do item.
    // Se encontrado, salva e retorna 200 (OK) com o item atualizado.
    // Se não encontrado, retorna 404 (Not Found).
    @PutMapping("/{id}")
    public ResponseEntity<ItemVenda> updateItemVenda(@PathVariable Integer id, @RequestBody ItemVenda itemVendaDetails) {
        Optional<ItemVenda> optionalItemVenda = itemVendaService.findById(id);
        if (optionalItemVenda.isPresent()) {
            ItemVenda itemVenda = optionalItemVenda.get();
            itemVenda.setProduto(itemVendaDetails.getProduto());
            itemVenda.setQuantidade(itemVendaDetails.getQuantidade());
            itemVenda.setPrecoUnitario(itemVendaDetails.getPrecoUnitario());
            ItemVenda updatedItemVenda = itemVendaService.save(itemVenda);
            return ResponseEntity.ok(updatedItemVenda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para DELETAR um item de venda pelo seu ID.
    // @DeleteMapping("/{id}") mapeia requisições HTTP DELETE com ID na URL.
    // Verifica se o item existe antes de tentar deletar.
    // Se existir, deleta e retorna 204 (No Content) — sucesso sem corpo de resposta.
    // Se não existir, retorna 404 (Not Found).
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemVenda(@PathVariable Integer id) {
        Optional<ItemVenda> itemVenda = itemVendaService.findById(id);
        if (itemVenda.isPresent()) {
            itemVendaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
