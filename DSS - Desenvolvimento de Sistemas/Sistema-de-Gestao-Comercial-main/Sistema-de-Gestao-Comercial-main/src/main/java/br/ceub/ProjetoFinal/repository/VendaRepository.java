package br.ceub.ProjetoFinal.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ceub.ProjetoFinal.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {

	@Query("SELECT c FROM Venda c")
	public List<Venda> findAll();
	
	@Query("SELECT c FROM Venda c WHERE c.id = :id")
	public Optional<Venda> findById(Integer id);
	
	@Query("SELECT c FROM Venda c WHERE c.data = :data")
	public Optional<Venda> findByData(LocalDate data);

	@Transactional
	@Modifying
	@Query("DELETE FROM Venda c WHERE c.id = :id")
	public void deleteById(Integer id);
	
	@Query("SELECT c FROM Venda c WHERE c.cliente.nome = :nome")
	public List<Venda> findByNome(String nome);
	

	
}
