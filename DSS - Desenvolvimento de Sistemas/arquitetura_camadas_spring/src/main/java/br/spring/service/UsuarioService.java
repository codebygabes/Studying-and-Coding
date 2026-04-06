package br.spring.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.spring.model.Usuario;
import br.spring.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public Usuario cadastrar(Usuario usuario) {
        if (usuario == null || usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
        if (usuario.getEmail() == null || !EMAIL_PATTERN.matcher(usuario.getEmail()).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (usuario.getCpf() == null || usuario.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        // Verificar se email ou CPF já existe
        if (usuarioRepository.buscarPorEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (usuarioRepository.buscarPorCpf(usuario.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        return usuarioRepository.salvar(usuario);
    }

    public Usuario buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return usuarioRepository.buscarPorId(id);
    }

    public Usuario buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        return usuarioRepository.buscarPorCpf(cpf);
    }

    public Usuario buscarPorEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }
        return usuarioRepository.buscarPorEmail(email);
    }

    public List<Usuario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome para busca é obrigatório");
        }
        return usuarioRepository.buscarPorNome(nome);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public void alterar(Usuario usuario) {
        if (usuario == null || usuario.getId() <= 0) {
            throw new IllegalArgumentException("Usuário inválido para alteração");
        }
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
        if (usuario.getEmail() == null || !EMAIL_PATTERN.matcher(usuario.getEmail()).matches()) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (usuario.getCpf() == null || usuario.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }
        usuarioRepository.atualizar(usuario);
    }

    public void remover(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        usuarioRepository.deletar(id);
    }
}
