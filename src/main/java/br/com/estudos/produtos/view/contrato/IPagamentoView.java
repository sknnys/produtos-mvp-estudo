package br.com.estudos.produtos.view.contrato;

public interface IPagamentoView extends IJanelaView {
    String getFormaPagamento();
    void mostrarResumo(String numero, String cliente, String dataHora,
            String valorItens, String taxaFinal, String descontoCupom, String totalPagar);
    void mostrarItens(String[][] linhas);
    void mostrarCamposForma(String formaPagamento);
    void mostrarConfirmacao(String texto);
    void definirFinalizado(boolean finalizado);
    void aoAlterarForma(Runnable acao);
    void aoFinalizar(Runnable acao);
    void aoVisualizarPedido(Runnable acao);
    void aoFechar(Runnable acao);
}
