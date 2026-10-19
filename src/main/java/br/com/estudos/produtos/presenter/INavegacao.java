/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.presenter;

import br.com.estudos.produtos.model.Usuario;

public interface INavegacao {
    void incluirProduto();
    void buscarProdutos();
    void categorias();
    void clientes();
    void pedidos(Usuario usuario);
    void usuarios(Usuario usuario);
    void calcular();
    void visualizarProduto(int id);
    void editarProduto(int id);
    void historicoProduto(int id);
    void sair();
}

