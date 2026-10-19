/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.presenter;

import br.com.estudos.produtos.model.Pedido;
import br.com.estudos.produtos.servico.PedidoServico;
import br.com.estudos.produtos.view.contrato.IPedidosView;
import java.util.ArrayList;
import java.util.List;

public class PedidosPresenter {
    private IPedidosView view;
    private PedidoServico service;
    private List<Pedido> pedidos = new ArrayList<>();

    public PedidosPresenter(IPedidosView view, PedidoServico service) {
        this.view = view;
        this.service = service;

        view.aoSelecionar(new Runnable() {
            @Override
            public void run() {
                selecionar();
            }
        });
        view.aoFechar(new Runnable() {
            @Override
            public void run() {
                view.fechar();
            }
        });
        atualizar();
    }

    private void atualizar() {
        pedidos = service.listarTodos();
        String[][] linhas = new String[pedidos.size()][4];

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedido = pedidos.get(i);
            linhas[i] = new String[]{
                pedido.getIdentificador(),
                pedido.getNomeCliente(),
                pedido.getSituacao(),
                Formatos.numero(pedido.getValorTotal())
            };
        }

        view.mostrarPedidos(linhas);
        view.selecionarLinha(pedidos.isEmpty() ? -1 : 0);
        selecionar();
    }

    private void selecionar() {
        int indice = view.getLinha();

        if (indice < 0 || indice >= pedidos.size()) {
            view.mostrarDados("", "", "", "", "");
            return;
        }

        Pedido pedido = pedidos.get(indice);
        view.mostrarDados(
                pedido.getIdentificador(),
                pedido.getNomeCliente(),
                pedido.getSituacao(),
                Formatos.numero(pedido.getValorTotal()),
                pedido.getEnderecoPedido()
        );
    }
}
