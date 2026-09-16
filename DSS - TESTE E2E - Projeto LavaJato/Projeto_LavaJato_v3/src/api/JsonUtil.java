package api;

import model.Cliente;
import model.Funcionario;
import model.Servico;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilitário de JSON feito à mão, sem nenhuma biblioteca externa
 * (nada de Gson/Jackson). Serve só para os objetos simples deste projeto.
 *
 * Limitação conhecida: o parser (parseObjetoSimples) assume um JSON "plano"
 * de um nível, sem vírgulas dentro dos valores. É suficiente para os
 * cadastros deste sistema, mas não é um parser JSON genérico.
 */
public class JsonUtil {

    // ===================== SERIALIZAÇÃO (objeto -> JSON) =====================

    public static String clienteParaJson(Cliente c) {
        return "{"
                + "\"idCliente\":" + c.getIdCliente() + ","
                + "\"nome\":\"" + escapar(c.getNome()) + "\","
                + "\"telefone\":\"" + escapar(c.getTelefone()) + "\","
                + "\"placa\":\"" + escapar(c.getPlaca()) + "\","
                + "\"modeloVeiculo\":\"" + escapar(c.getModeloVeiculo()) + "\","
                + "\"marcaVeiculo\":\"" + escapar(c.getMarcaVeiculo()) + "\""
                + "}";
    }

    public static String clientesParaJson(List<Cliente> clientes) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < clientes.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(clienteParaJson(clientes.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    public static String funcionarioParaJson(Funcionario f) {
        return "{"
                + "\"idFuncionario\":" + f.getIdFuncionario() + ","
                + "\"nome\":\"" + escapar(f.getNome()) + "\","
                + "\"cargo\":\"" + escapar(f.getCargo()) + "\","
                + "\"telefone\":\"" + escapar(f.getTelefone()) + "\""
                + "}";
    }

    public static String funcionariosParaJson(List<Funcionario> funcionarios) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < funcionarios.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(funcionarioParaJson(funcionarios.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    public static String servicoParaJson(Servico s) {
        return "{"
                + "\"idServico\":" + s.getIdServico() + ","
                + "\"nomeServico\":\"" + escapar(s.getNomeServico()) + "\","
                + "\"descricao\":\"" + escapar(s.getDescricao()) + "\","
                + "\"valor\":" + s.getValor()
                + "}";
    }

    public static String servicosParaJson(List<Servico> servicos) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < servicos.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(servicoParaJson(servicos.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }

    public static String escapar(String valor) {
        if (valor == null) return "";
        return valor.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // ===================== PARSE (JSON -> Map) =====================

    /**
     * Faz o parse de um objeto JSON "plano", de um nível só, tipo:
     * {"nome":"João","telefone":"6199999999","valor":49.90}
     * Retorna um Map<chave, valor-em-texto> para o chamador converter
     * pro tipo que precisar (int, double, etc).
     */
    public static Map<String, String> parseObjetoSimples(String json) {

        Map<String, String> resultado = new HashMap<String, String>();

        if (json == null || json.trim().isEmpty()) {
            return resultado;
        }

        String conteudo = json.trim();

        if (conteudo.startsWith("{")) {
            conteudo = conteudo.substring(1);
        }
        if (conteudo.endsWith("}")) {
            conteudo = conteudo.substring(0, conteudo.length() - 1);
        }

        String[] pares = conteudo.split(",");

        for (String par : pares) {

            String[] chaveValor = par.split(":", 2);

            if (chaveValor.length < 2) {
                continue;
            }

            String chave = chaveValor[0].trim().replaceAll("^\"|\"$", "");
            String valor = chaveValor[1].trim();

            if (valor.startsWith("\"") && valor.endsWith("\"") && valor.length() >= 2) {
                valor = valor.substring(1, valor.length() - 1);
            }

            resultado.put(chave, valor);
        }

        return resultado;
    }
}
