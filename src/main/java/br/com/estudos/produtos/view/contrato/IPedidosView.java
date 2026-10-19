/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.estudos.produtos.view.contrato;

public interface IPedidosView extends IJanelaView {
    int getLinha();
    void mostrarPedidos(String[][] linhas);
    void mostrarDados(String numero, String cliente, String situacao,
            String valor, String endereco);
    void selecionarLinha(int indice);
    void aoSelecionar(Runnable acao);
    void aoFechar(Runnable acao);
}
