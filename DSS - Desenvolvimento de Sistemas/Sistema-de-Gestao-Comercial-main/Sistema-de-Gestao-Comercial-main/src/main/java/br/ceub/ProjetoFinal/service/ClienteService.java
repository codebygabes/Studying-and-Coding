package br.ceub.ProjetoFinal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import br.ceub.ProjetoFinal.model.Cliente;
import br.ceub.ProjetoFinal.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente> findAll() {
        return clienteRepository.findAll(); 
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> findByNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

}
