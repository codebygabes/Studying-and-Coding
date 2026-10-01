package services;

import dao.ClienteDAO;
import model.Cliente;

import java.util.List;

public class ClienteService {

    private ClienteDAO dao = new ClienteDAO();

    public void cadastrarCliente(Cliente cliente) {

        validarCliente(cliente);
        dao.inserir(cliente);

    }

    public List<Cliente> listarClientes() {

        return dao.listar();

    }

    public void atualizarCliente(Cliente cliente) {

        if (cliente.getIdCliente() <= 0) {
            throw new IllegalArgumentException(
                "ID do cliente inválido para atualização.");
        }

        validarCliente(cliente);
        dao.atualizar(cliente);

    }

    public void excluirCliente(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "ID do cliente inválido para exclusão.");
        }

        dao.excluir(id);

    }

    private void validarCliente(Cliente cliente) {

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O nome do cliente é obrigatório.");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O telefone do cliente é obrigatório.");
        }

        if (cliente.getPlaca() == null || cliente.getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "A placa do veículo é obrigatória.");
        }

        String placa = cliente.getPlaca().toUpperCase().replaceAll("[^A-Z0-9]", "");
        if (!placa.matches("[A-Z]{3}[0-9]{4}") && !placa.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}")) {
            throw new IllegalArgumentException(
                "Placa inválida. Use o formato ABC1234 ou ABC1D23 (Mercosul).");
        }

        if (cliente.getModeloVeiculo() == null || cliente.getModeloVeiculo().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O modelo do veículo é obrigatório.");
        }

        if (cliente.getMarcaVeiculo() == null || cliente.getMarcaVeiculo().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "A marca do veículo é obrigatória.");
        }

    }

}
