package br.ceub.ProjetoFinal.controller;

import java.util.List;
import java.util.Optional;

import br.ceub.ProjetoFinal.dto.ClientUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import br.ceub.ProjetoFinal.model.Cliente;
import br.ceub.ProjetoFinal.service.ClienteService;

// @RestController indica que esta classe é um controlador REST, ou seja,
// todos os métodos retornam dados diretamente no corpo da resposta HTTP (JSON).
// @RequestMapping define o caminho base da URL para todos os endpoints desta classe.
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	// @Autowired injeta automaticamente a dependência do ClienteService,
	// permitindo que o controller use os métodos de negócio sem instanciar manualmente.
	@Autowired
	private ClienteService clienteService;

	// Endpoint para CRIAR um novo cliente.
	// @PostMapping mapeia requisições HTTP POST para este método.
	// @RequestBody faz o Spring converter o JSON recebido no corpo da requisição para um objeto Cliente.
	// Retorna o cliente salvo com status HTTP 200 (OK).
	@PostMapping
	public ResponseEntity<Cliente> createLivro(@RequestBody Cliente cliente) {
		Cliente savedCliente = clienteService.save(cliente);
		return ResponseEntity.ok(savedCliente);
	}

	// Endpoint para LISTAR todos os clientes cadastrados.
	// @GetMapping mapeia requisições HTTP GET sem parâmetro de ID.
	// Retorna uma lista de todos os clientes com status HTTP 200 (OK).
	@GetMapping
	public ResponseEntity<List<Cliente>> getAllCliente() {
		List<Cliente> clientes = clienteService.findAll();
		return ResponseEntity.ok(clientes);
	}

	// Endpoint para BUSCAR um cliente específico pelo seu ID.
	// @GetMapping("/{id}") mapeia GET com um ID na URL, ex: GET /api/clientes/1
	// @PathVariable extrai o valor do {id} da URL e o passa como parâmetro.
	// Se o cliente for encontrado, retorna 200 (OK); caso contrário, retorna 404 (Not Found).
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable long id) {
		Optional<Cliente> Cliente = clienteService.findById((long)id);
		return Cliente.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	// Endpoint para PESQUISAR clientes pelo nome.
	// @GetMapping("/search") mapeia GET para /api/clientes/search
	// @RequestParam extrai o parâmetro "nome" da query string da URL, ex: ?nome=João
	// Retorna o cliente encontrado (ou vazio) com status HTTP 200 (OK).
	@GetMapping("/search")
    public ResponseEntity<Optional<Cliente>> searchClientes(@RequestParam String nome) {
        Optional<Cliente> clientes = clienteService.findByNome(nome);
        return ResponseEntity.ok(clientes);
    }

	// Endpoint para ATUALIZAR parcialmente um cliente existente (atualização parcial via PATCH).
	// @PatchMapping("/{id}") mapeia requisições HTTP PATCH com ID na URL.
	// @RequestBody recebe apenas os campos que devem ser atualizados (DTO ClientUpdate).
	// Busca o cliente pelo ID; se não existir, retorna 404.
	// Atualiza somente os campos que não são nulos e não estão em branco.
	// Salva e retorna o cliente atualizado com status 200 (OK).
	@PatchMapping("/{id}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody ClientUpdate clienteDetails) {

		Optional<Cliente> optionalCliente = clienteService.findById(id);

		if (optionalCliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Cliente cliente = optionalCliente.get();

		// Atualiza o nome apenas se o novo valor não for nulo ou vazio
		if (clienteDetails.nome() != null && !clienteDetails.nome().isBlank()) {
			cliente.setNome(clienteDetails.nome());
		}

		// Atualiza o email apenas se o novo valor não for nulo ou vazio
		if (clienteDetails.email() != null && !clienteDetails.email().isBlank()) {
			cliente.setEmail(clienteDetails.email());
		}

		// Atualiza o CPF apenas se o novo valor não for nulo ou vazio
		if (clienteDetails.cpf() != null && !clienteDetails.cpf().isBlank()) {
			cliente.setCpf(clienteDetails.cpf());
		}

		// Atualiza o endereço apenas se o novo valor não for nulo ou vazio
		if (clienteDetails.endereco() != null && !clienteDetails.endereco().isBlank()) {
			cliente.setEndereco(clienteDetails.endereco());
		}

		// Atualiza o telefone apenas se o novo valor não for nulo
		if (clienteDetails.telefone() != null) {
			cliente.setTelefone(clienteDetails.telefone());
		}

		Cliente updatedCliente = clienteService.save(cliente);
		return ResponseEntity.ok(updatedCliente);
	}

	// Endpoint para DELETAR um cliente pelo seu ID.
	// @DeleteMapping("/{id}") mapeia requisições HTTP DELETE com ID na URL.
	// Verifica se o cliente existe antes de deletar.
	// Se existir, deleta e retorna 204 (No Content) — sucesso sem corpo de resposta.
	// Se não existir, retorna 404 (Not Found).
	 @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
		 Optional<Cliente> cliente = clienteService.findById(id);

		 if (cliente.isPresent()) {
				 clienteService.deleteById(id);
				 return ResponseEntity.noContent().build();
	    }
		 return ResponseEntity.notFound().build();

}}
