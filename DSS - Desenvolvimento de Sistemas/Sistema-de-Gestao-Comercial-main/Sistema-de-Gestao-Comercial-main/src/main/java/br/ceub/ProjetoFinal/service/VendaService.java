package br.ceub.ProjetoFinal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ceub.ProjetoFinal.model.Venda;
import br.ceub.ProjetoFinal.repository.VendaRepository;

@Service
public class VendaService {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	public Venda save(Venda venda) {
		return vendaRepository.save(venda);
	}
	
	public List<Venda> findAll() {
		return vendaRepository.findAll();
	}
	
	public Optional<Venda> findById(Integer id) {
		return vendaRepository.findById(id);
	}
	
	public Optional<Venda> findByData(LocalDate data) {
		return vendaRepository.findByData(data);
	}
	
	public List<Venda> findByNome(String nome) {
		return vendaRepository.findByNome(nome);
	}

	public void deleteById(Integer id) {
		vendaRepository.deleteById(id);
	}

}
