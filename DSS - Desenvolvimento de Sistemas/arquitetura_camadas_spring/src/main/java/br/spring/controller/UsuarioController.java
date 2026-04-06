package br.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.spring.model.Usuario;
import br.spring.service.UsuarioService;

@Component
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    public Usuario cadastrar(Usuario usuario) {
        return usuarioService.cadastrar(usuario);
    }

    public Usuario buscarPorId(int id) {
        return usuarioService.buscarPorId(id);
    }

    public Usuario buscarPorCpf(String cpf) {
        return usuarioService.buscarPorCpf(cpf);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioService.buscarPorEmail(email);
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioService.buscarPorNome(nome);
    }

    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    public void alterar(Usuario usuario) {
        usuarioService.alterar(usuario);
    }

    public void remover(int id) {
        usuarioService.remover(id);
    }
}