package br.ceub.ProjetoFinal.repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ceub.ProjetoFinal.model.ItemVenda;
import br.ceub.ProjetoFinal.model.Venda;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Integer> {

	@Query("SELECT c FROM ItemVenda c")
	public List<ItemVenda> findAll();
	
	@Query("SELECT c FROM ItemVenda c WHERE c.id = :id")
	public Optional<ItemVenda> findById(Integer id);
	
	@Query("SELECT c FROM ItemVenda c WHERE c.venda = :vendaId")
	public Optional<ItemVenda> findByVendaId(Integer vendaId);
	
	@Query("SELECT c FROM ItemVenda c WHERE c.produto.nome = :nome")
	public List<ItemVenda> findByNome(String nome);

	@Transactional
	@Modifying
	@Query("DELETE FROM ItemVenda c WHERE c.id = :id")
	public void deleteById(Integer id);

	
}
