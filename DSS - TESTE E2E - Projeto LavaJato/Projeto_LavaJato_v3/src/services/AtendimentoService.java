package services;

import dao.AtendimentoDAO;
import dao.AtendimentoServicoDAO;
import model.Atendimento;
import model.AtendimentoServico;

import java.time.LocalDateTime;
import java.util.List;

public class AtendimentoService {

    private AtendimentoDAO dao = new AtendimentoDAO();
    private AtendimentoServicoDAO atendimentoServicoDAO =
            new AtendimentoServicoDAO();

    public void cadastrarAtendimento(
            Atendimento atendimento,
            List<AtendimentoServico> servicos) {

        validarAtendimento(atendimento);

        if (servicos == null || servicos.isEmpty()) {
            throw new IllegalArgumentException(
                "Um atendimento deve ter pelo menos um serviço.");
        }

        if (atendimento.getDataAtendimento() == null) {
            atendimento.setDataAtendimento(LocalDateTime.now());
        }

        dao.inserir(atendimento);

        for (AtendimentoServico as : servicos) {

            if (as.getQuantidade() <= 0) {
                throw new IllegalArgumentException(
                    "A quantidade de cada serviço deve ser maior que zero.");
            }

            atendimentoServicoDAO.inserir(as);

        }

    }

    public List<Atendimento> listarAtendimentos() {

        return dao.listar();

    }

    public void atualizarAtendimento(Atendimento atendimento) {

        if (atendimento.getIdAtendimento() <= 0) {
            throw new IllegalArgumentException(
                "ID do atendimento inválido para atualização.");
        }

        validarAtendimento(atendimento);
        dao.atualizar(atendimento);

    }

    public void excluirAtendimento(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "ID do atendimento inválido para exclusão.");
        }

        dao.excluir(id);

    }

    // Regra de negócio: calcula o total do atendimento
    public double calcularTotal(List<AtendimentoServico> servicos) {

        if (servicos == null || servicos.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;

        for (AtendimentoServico as : servicos) {
            total += as.getServico().getValor() * as.getQuantidade();
        }

        return total;

    }

    private void validarAtendimento(Atendimento atendimento) {

        if (atendimento.getCliente() == null ||
            atendimento.getCliente().getIdCliente() <= 0) {
            throw new IllegalArgumentException(
                "O cliente do atendimento é obrigatório.");
        }

        if (atendimento.getFuncionario() == null ||
            atendimento.getFuncionario().getIdFuncionario() <= 0) {
            throw new IllegalArgumentException(
                "O funcionário responsável é obrigatório.");
        }

    }

}
