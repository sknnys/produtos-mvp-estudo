/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.estudos.produtos.view.contrato;

public interface IPedidosView extends IJanelaView {
    String getCliente();
    String getEndereco();
    String getProduto();
    String getQuantidade();
    String getCodigoCupom();
    int getLinha();
    int getLinhaItem();
    void mostrarClientes(String[] clientes);
    void mostrarProdutos(String[] produtos);
    void mostrarPedidos(String[][] linhas);
    void mostrarItens(String[][] linhas);
    void mostrarDados(String numero, String data, String cliente,
            String situacao, String valor, String endereco);
    void selecionarLinha(int indice);
    void selecionarLinhaItem(int indice);
    void selecionarCliente(String cliente);
    void definirClienteFixo(boolean fixo);
    void mostrarEndereco(String endereco);
    void mostrarQuantidade(String quantidade);
    void mostrarResumo(String taxaPadrao, String descontosTaxa,
            String taxaFinal, String descontoCupom, String totalPagar,
            String cupom);
    void definirDetalhesHabilitado(boolean habilitado);
    void definirSomenteLeitura(boolean somenteLeitura);
    void aoNovo(Runnable acao);
    void aoSalvarEndereco(Runnable acao);
    void aoSelecionarCliente(Runnable acao);
    void aoAdicionarProduto(Runnable acao);
    void aoAlterarQuantidade(Runnable acao);
    void aoSelecionarItem(Runnable acao);
    void aoSelecionar(Runnable acao);
    void aoAplicarCupom(Runnable acao);
    void aoRemoverCupom(Runnable acao);
    void aoCalcularValor(Runnable acao);
    void aoDetalhes(Runnable acao);
    void aoPagar(Runnable acao);
    void aoFechar(Runnable acao);
}
