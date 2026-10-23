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
    int getLinha();
    int getLinhaItem();
    void mostrarClientes(String[] clientes);
    void mostrarProdutos(String[] produtos);
    void mostrarPedidos(String[][] linhas);
    void mostrarItens(String[][] linhas);
    void mostrarDados(String numero, String cliente, String situacao,
            String valor, String endereco);
    void selecionarLinha(int indice);
    void selecionarLinhaItem(int indice);
    void selecionarCliente(String cliente);
    void definirClienteFixo(boolean fixo);
    void mostrarEndereco(String endereco);
    void mostrarQuantidade(String quantidade);
    void aoNovo(Runnable acao);
    void aoSalvarEndereco(Runnable acao);
    void aoSelecionarCliente(Runnable acao);
    void aoAdicionarProduto(Runnable acao);
    void aoAlterarQuantidade(Runnable acao);
    void aoSelecionarItem(Runnable acao);
    void aoSelecionar(Runnable acao);
    void aoFechar(Runnable acao);
}
