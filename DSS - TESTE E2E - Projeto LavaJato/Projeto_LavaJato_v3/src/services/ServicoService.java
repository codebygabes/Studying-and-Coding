package services;

import dao.ServicoDAO;
import model.Servico;

import java.util.List;

public class ServicoService {

    private ServicoDAO dao = new ServicoDAO();

    public void cadastrarServico(Servico servico) {

        validarServico(servico);
        dao.inserir(servico);

    }

    public List<Servico> listarServicos() {

        return dao.listar();

    }

    public void atualizarServico(Servico servico) {

        if (servico.getIdServico() <= 0) {
            throw new IllegalArgumentException(
                "ID do serviço inválido para atualização.");
        }

        validarServico(servico);
        dao.atualizar(servico);

    }

    public void excluirServico(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "ID do serviço inválido para exclusão.");
        }

        dao.excluir(id);

    }

    private void validarServico(Servico servico) {

        if (servico.getNomeServico() == null || servico.getNomeServico().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O nome do serviço é obrigatório.");
        }

        if (servico.getValor() <= 0) {
            throw new IllegalArgumentException(
                "O valor do serviço deve ser maior que zero.");
        }

    }

}
