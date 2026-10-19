/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.view.contrato;

public interface IPrincipalView extends IJanelaView {
    void exibirUsuario(String nome, String perfil);
    void configurarPerfil(String perfil);
    void aoIncluir(Runnable acao);
    void aoBuscar(Runnable acao);
    void aoCategorias(Runnable acao);
    void aoClientes(Runnable acao);
    void aoPedidos(Runnable acao);
    void aoUsuarios(Runnable acao);
    void aoCalcular(Runnable acao);
    void aoSair(Runnable acao);
}

