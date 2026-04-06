package br.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.spring.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
	
	public List<Livro> findLivroByNome(String name);

}
