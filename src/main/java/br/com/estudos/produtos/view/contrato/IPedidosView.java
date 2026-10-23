/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.estudos.produtos.view.contrato;

public interface IPedidosView extends IJanelaView {
    String getCliente();
    String getEndereco();
    int getLinha();
    void mostrarClientes(String[] clientes);
    void mostrarPedidos(String[][] linhas);
    void mostrarDados(String numero, String cliente, String situacao,
            String valor, String endereco);
    void selecionarLinha(int indice);
    void selecionarCliente(String cliente);
    void definirClienteFixo(boolean fixo);
    void mostrarEndereco(String endereco);
    void aoNovo(Runnable acao);
    void aoSalvarEndereco(Runnable acao);
    void aoSelecionarCliente(Runnable acao);
    void aoSelecionar(Runnable acao);
    void aoFechar(Runnable acao);
}
