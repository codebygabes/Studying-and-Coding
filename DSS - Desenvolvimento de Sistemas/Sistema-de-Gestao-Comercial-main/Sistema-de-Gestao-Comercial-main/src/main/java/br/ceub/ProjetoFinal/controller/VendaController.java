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

import br.ceub.ProjetoFinal.model.Produto;
import br.ceub.ProjetoFinal.model.Venda;
import br.ceub.ProjetoFinal.service.VendaService;

// @RestController indica que esta classe é um controlador REST:
// os métodos retornam dados diretamente no corpo da resposta HTTP (JSON).
// @RequestMapping define o caminho base "/api/vendas" para todos os endpoints desta classe.
@RestController
@RequestMapping("/api/vendas")
public class VendaController {

// @Autowired injeta automaticamente a dependência do VendaService,
// permitindo o uso dos métodos de negócio sem precisar instanciar manualmente.
@Autowired
	private VendaService vendaService;

	// Endpoint para REGISTRAR uma nova venda.
	// @PostMapping mapeia requisições HTTP POST para este método.
	// @RequestBody converte o JSON recebido no corpo da requisição em um objeto Venda.
	// Salva a venda no banco de dados e retorna o objeto salvo com status HTTP 200 (OK).
	@PostMapping
	public ResponseEntity<Venda> createVenda(@RequestBody Venda venda) {
		Venda savedVenda = vendaService.save(venda);
		return ResponseEntity.ok(savedVenda);
	}

	// Endpoint para LISTAR todas as vendas cadastradas.
	// @GetMapping sem parâmetro mapeia GET para /api/vendas.
	// Retorna uma lista completa de vendas com status HTTP 200 (OK).
	@GetMapping
	public ResponseEntity<List<Venda>> getAllVenda() {
		List<Venda> Vendas = vendaService.findAll();
		return ResponseEntity.ok(Vendas);
	}

	// Endpoint para BUSCAR uma venda específica pelo seu ID.
	// @GetMapping("/{id}") mapeia GET com ID na URL, ex: GET /api/vendas/3
	// @PathVariable extrai o valor do {id} diretamente da URL.
	// Se encontrada, retorna 200 (OK) com a venda; caso contrário, retorna 404 (Not Found).
	@GetMapping("/{id}")
	public ResponseEntity<Venda> getVendaById(@PathVariable Integer id) {
		Optional<Venda> Venda = vendaService.findById(id);
		return Venda.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	// Endpoint para PESQUISAR vendas pelo nome do cliente ou outro critério.
	// @GetMapping("/search") mapeia GET para /api/vendas/search
	// @RequestParam extrai o parâmetro "nome" da query string, ex: ?nome=João
	// Retorna uma lista de vendas que correspondem ao critério de busca.
	@GetMapping("/search")
    public ResponseEntity<List<Venda>> searchVendas(@RequestParam String nome) {
        List<Venda> Vendas = vendaService.findByNome(nome);
        return ResponseEntity.ok(Vendas);
    }
	
	// Endpoint para ATUALIZAR completamente uma venda existente (atualização total via PUT).
	// @PutMapping("/{id}") mapeia requisições HTTP PUT com ID na URL.
	// O PUT substitui todos os campos do objeto — diferente do PATCH que é parcial.
	// Atualiza data, valor total, cliente, usuário e quantidade da venda.
	// Se encontrada, salva e retorna 200 (OK) com a venda atualizada.
	// Se não encontrada, retorna 404 (Not Found).
	@PutMapping("/{id}")
    public ResponseEntity<Venda> updateVenda(@PathVariable Integer id, @RequestBody Venda vendaDetails) {
        Optional<Venda>optionalVenda = vendaService.findById(id);
        if (optionalVenda.isPresent()) {
            Venda venda = optionalVenda.get();
            venda.setData(vendaDetails.getData());
            venda.setValorTotal(vendaDetails.getValorTotal());
            venda.setClienteId(vendaDetails.getCliente());
            venda.setUsuarioId(vendaDetails.getUsuario());
            venda.setQuantidade(vendaDetails.getQuantidade());
            Venda updatedVenda = vendaService.save(venda);
            return ResponseEntity.ok(updatedVenda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	// Endpoint para DELETAR uma venda pelo seu ID.
	// @DeleteMapping("/{id}") mapeia requisições HTTP DELETE com ID na URL.
	// Verifica se a venda existe antes de tentar deletar.
	// Se existir, deleta e retorna 204 (No Content) — sucesso sem corpo de resposta.
	// Se não existir, retorna 404 (Not Found).
	 @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteVenda(@PathVariable Integer id) {
	        Optional<Venda> venda = vendaService.findById(id);
	        if (venda.isPresent()) {
	            vendaService.deleteById(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }


}
