package services;

import dao.FuncionarioDAO;
import model.Funcionario;

import java.util.List;

public class FuncionarioService {

    private FuncionarioDAO dao = new FuncionarioDAO();

    public void cadastrarFuncionario(Funcionario funcionario) {

        validarFuncionario(funcionario);
        dao.inserir(funcionario);

    }

    public List<Funcionario> listarFuncionarios() {

        return dao.listar();

    }

    public void atualizarFuncionario(Funcionario funcionario) {

        if (funcionario.getIdFuncionario() <= 0) {
            throw new IllegalArgumentException(
                "ID do funcionário inválido para atualização.");
        }

        validarFuncionario(funcionario);
        dao.atualizar(funcionario);

    }

    public void excluirFuncionario(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "ID do funcionário inválido para exclusão.");
        }

        dao.excluir(id);

    }

    private void validarFuncionario(Funcionario funcionario) {

        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O nome do funcionário é obrigatório.");
        }

        if (funcionario.getTelefone() == null || funcionario.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O telefone do funcionário é obrigatório.");
        }

        if (funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O cargo do funcionário é obrigatório.");
        }

    }

}
