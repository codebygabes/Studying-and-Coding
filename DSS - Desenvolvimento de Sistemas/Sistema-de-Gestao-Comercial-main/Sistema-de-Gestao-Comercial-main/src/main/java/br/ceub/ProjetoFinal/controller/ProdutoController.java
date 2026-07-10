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

import br.ceub.ProjetoFinal.model.Cliente;
import br.ceub.ProjetoFinal.model.Produto;
import br.ceub.ProjetoFinal.service.ProdutoService;


// @RestController indica que esta classe é um controlador REST:
// os métodos retornam dados diretamente no corpo da resposta HTTP (JSON).
// @RequestMapping define o caminho base "/api/produtos" para todos os endpoints desta classe.
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	
	// @Autowired injeta automaticamente a dependência do ProdutoService,
	// permitindo o uso dos métodos de negócio sem precisar instanciar manualmente.
	@Autowired
	private ProdutoService produtoService;

	// Endpoint para CRIAR um novo produto.
	// @PostMapping mapeia requisições HTTP POST para este método.
	// @RequestBody converte o JSON recebido no corpo da requisição em um objeto Produto.
	// Salva o produto e retorna o objeto salvo com status HTTP 200 (OK).
	@PostMapping
	public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
		Produto savedProduto = produtoService.save(produto);
		return ResponseEntity.ok(savedProduto);
	}

	// Endpoint para LISTAR todos os produtos cadastrados.
	// @GetMapping sem parâmetro mapeia GET para /api/produtos.
	// Retorna uma lista completa de produtos com status HTTP 200 (OK).
	@GetMapping
	public ResponseEntity<List<Produto>> getAllProduto() {
		List<Produto> Produtos = produtoService.findAll();
		return ResponseEntity.ok(Produtos);
	}

	// Endpoint para BUSCAR um produto específico pelo seu ID.
	// @GetMapping("/{id}") mapeia GET com ID na URL, ex: GET /api/produtos/5
	// @PathVariable extrai o valor do {id} diretamente da URL.
	// Se encontrado, retorna 200 (OK) com o produto; caso contrário, retorna 404 (Not Found).
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getProdutoById(@PathVariable Integer id) {
		Optional<Produto> Produto = produtoService.findById(id);
		return Produto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	// Endpoint para PESQUISAR produtos pelo nome.
	// @GetMapping("/search") mapeia GET para /api/produtos/search
	// @RequestParam extrai o parâmetro "nome" da query string, ex: ?nome=Camiseta
	// Retorna uma lista de produtos cujo nome corresponde ao critério de busca.
	@GetMapping("/search")
    public ResponseEntity<List<Produto>> searchProdutos(@RequestParam String nome) {
        List<Produto> produtos = produtoService.findByNome(nome);
        return ResponseEntity.ok(produtos);
    }
	
	// Endpoint para ATUALIZAR completamente um produto existente (atualização total via PUT).
	// @PutMapping("/{id}") mapeia requisições HTTP PUT com ID na URL.
	// Ao contrário do PATCH, o PUT substitui todos os campos do objeto.
	// Se o produto for encontrado, atualiza nome, descrição, preço e quantidade,
	// salva as alterações e retorna 200 (OK) com o produto atualizado.
	// Se não encontrado, retorna 404 (Not Found).
	@PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Integer id, @RequestBody Produto produtoDetails) {
        Optional<Produto>optionalProduto = produtoService.findById(id);
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(produtoDetails.getNome());
            produto.setDescricao(produtoDetails.getDescricao());
            produto.setPreco(produtoDetails.getPreco());
            produto.setQuantidade(produtoDetails.getQuantidade());
            Produto updatedProduto = produtoService.save(produto);
            return ResponseEntity.ok(updatedProduto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	// Endpoint para DELETAR um produto pelo seu ID.
	// @DeleteMapping("/{id}") mapeia requisições HTTP DELETE com ID na URL.
	// Verifica se o produto existe antes de tentar deletar.
	// Se existir, deleta e retorna 204 (No Content) — sucesso sem corpo de resposta.
	// Se não existir, retorna 404 (Not Found).
	 @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteProduto(@PathVariable Integer id) {
	        Optional<Produto> produto = produtoService.findById(id);
	        if (produto.isPresent()) {
	            produtoService.deleteById(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

}
