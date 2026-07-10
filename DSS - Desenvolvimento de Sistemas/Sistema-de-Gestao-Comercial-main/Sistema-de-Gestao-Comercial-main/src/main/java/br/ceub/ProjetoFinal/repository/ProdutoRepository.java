package br.ceub.ProjetoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ceub.ProjetoFinal.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p")
    public List<Produto> findAll();

    @Query("SELECT p FROM Produto p WHERE p.id = :id")
    public Optional<Produto> findById(Integer id);

    @Query("SELECT p FROM Produto p WHERE p.nome = :nome")
    public List<Produto> findByNome(String nome);

}
