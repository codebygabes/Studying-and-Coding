package br.spring.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.spring.model.Usuario;

@Repository
public class UsuarioRepository {
    private static List<Usuario> usuarios = new ArrayList<>();

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == 0) {
            int novoId = 0;
            for (Usuario u : usuarios) {
                if (u.getId() > novoId) {
                    novoId = u.getId();
                }
            }
            novoId++;
            usuario.setId(novoId);
        }
        usuarios.add(usuario);
        return usuario;
    }

    public Usuario buscarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public Usuario buscarPorCpf(String cpf) {
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf)) {
                return u;
            }
        }
        return null;
    }

    public Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> resultado = new ArrayList<>();
        String nomeLower = nome.toLowerCase();
        for (Usuario u : usuarios) {
            if (u.getNome().toLowerCase().contains(nomeLower)) {
                resultado.add(u);
            }
        }
        return resultado;
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    public void atualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuario.getId()) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    public void deletar(int id) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == id) {
                usuarios.remove(i);
                break;
            }
        }
    }
}
