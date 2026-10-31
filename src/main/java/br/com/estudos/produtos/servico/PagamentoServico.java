package br.com.estudos.produtos.servico;

import br.com.estudos.produtos.model.Pedido;
import java.time.LocalDateTime;

public class PagamentoServico {
    public static final String PIX_CPF = "Pix por chave CPF";
    public static final String PIX_EMAIL = "Pix por chave e-mail";
    public static final String CARTAO_CREDITO = "Cartão de crédito à vista";

    private SimuladorFalhaPagamentoService simulador;

    public PagamentoServico(SimuladorFalhaPagamentoService simulador) {
        this.simulador = simulador;
    }

    public void finalizar(Pedido pedido, String formaPagamento) {
        if (pedido == null) {
            throw new RegraNegocioException("Informe um pedido.");
        }

        if (!pedido.isValoresCalculados()) {
            throw new RegraNegocioException("Calcule os valores antes de pagar o pedido.");
        }

        if (!pedido.estaNovo()) {
            throw new RegraNegocioException("O pagamento só pode ser registrado em pedido Novo.");
        }

        if (!formaValida(formaPagamento)) {
            throw new RegraNegocioException("Selecione uma forma de pagamento válida.");
        }

        simulador.simularFalha();
        pedido.registrarPagamento(formaPagamento, LocalDateTime.now());
    }

    private boolean formaValida(String formaPagamento) {
        return PIX_CPF.equals(formaPagamento)
                || PIX_EMAIL.equals(formaPagamento)
                || CARTAO_CREDITO.equals(formaPagamento);
    }
}
