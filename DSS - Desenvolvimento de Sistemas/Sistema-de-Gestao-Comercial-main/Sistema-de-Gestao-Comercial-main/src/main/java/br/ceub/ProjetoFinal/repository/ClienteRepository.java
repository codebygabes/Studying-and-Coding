package br.ceub.ProjetoFinal.repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ceub.ProjetoFinal.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	@Query("SELECT c FROM Cliente c")
	public List<Cliente> findAll();
	
	@Query("SELECT c FROM Cliente c WHERE c.id = :id")
	public Optional<Cliente> findById(Long id);
	
	@Query("SELECT c FROM Cliente c WHERE c.nome = :nome")
	public Optional<Cliente> findByNome(String nome);

	@Transactional
	@Modifying
	@Query("DELETE FROM Cliente c WHERE c.id = :id")
	public void deleteById(Long id);

}
