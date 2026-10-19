/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.presenter;

import br.com.estudos.produtos.model.Usuario;
import br.com.estudos.produtos.view.contrato.IPrincipalView;

public class PrincipalPresenter {
    public PrincipalPresenter(IPrincipalView view, INavegacao navegacao, Usuario usuario) {
        view.exibirUsuario(usuario.getNome(), usuario.getPerfil());
        view.configurarPerfil(usuario.getPerfil());

        view.aoIncluir(new Runnable() {
            @Override
            public void run() {
                navegacao.incluirProduto();
            }
        });
        view.aoBuscar(new Runnable() {
            @Override
            public void run() {
                navegacao.buscarProdutos();
            }
        });
        view.aoCategorias(new Runnable() {
            @Override
            public void run() {
                navegacao.categorias();
            }
        });
        view.aoClientes(new Runnable() {
            @Override
            public void run() {
                navegacao.clientes();
            }
        });
        view.aoPedidos(new Runnable() {
            @Override
            public void run() {
                navegacao.pedidos(usuario);
            }
        });
        view.aoUsuarios(new Runnable() {
            @Override
            public void run() {
                navegacao.usuarios(usuario);
            }
        });
        view.aoCalcular(new Runnable() {
            @Override
            public void run() {
                navegacao.calcular();
            }
        });

        view.aoSair(new Runnable() {
            @Override
            public void run() {
                view.fechar();
                navegacao.sair();
            }
        });
    }
}

