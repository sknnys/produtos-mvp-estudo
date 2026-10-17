/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.model;

import br.projeto.painelpedidos.model.PedidoAbstract;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends PedidoAbstract {
    private Cliente cliente;
    private String enderecoPedido;
    private List<ItemPedido> itens = new ArrayList<>();
    private double valorTotalPedido;
    private double creditoGeradoPedido;

    public Pedido(String identificador, LocalDateTime dataHora,
            String nomeCliente, String enderecoEntrega, double valorTotal) {
        super(identificador, dataHora, nomeCliente, enderecoEntrega, valorTotal);
        this.enderecoPedido = enderecoEntrega;
        this.valorTotalPedido = valorTotal;
    }

    public Pedido(String identificador, LocalDateTime dataHora,
            Cliente cliente, String enderecoEntrega) {
        this(identificador, dataHora, cliente.getNome(), enderecoEntrega, 0.0);
        this.cliente = cliente;
    }

    @Override
    public Pedido criarCopia(String novoIdentificador, LocalDateTime novaDataHora) {
        Pedido copia = new Pedido(novoIdentificador, novaDataHora,
                getNomeCliente(), enderecoPedido, 0.0);

        copia.cliente = cliente;

        for (ItemPedido item : itens) {
            copia.adicionarItem(item.getProduto(), item.getQuantidade());
        }

        copia.valorTotalPedido = valorTotalPedido;
        return copia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getEnderecoPedido() {
        return enderecoPedido;
    }

    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }

    public void adicionarItem(Produto produto, int quantidade) {
        for (ItemPedido item : itens) {
            if (item.getProduto().getId() == produto.getId()) {
                item.adicionarQuantidade(quantidade);
                atualizarValorTotal();
                return;
            }
        }

        itens.add(new ItemPedido(produto, quantidade));
        atualizarValorTotal();
    }

    public void alterarQuantidadeItem(int produtoId, int quantidade) {
        for (int i = 0; i < itens.size(); i++) {
            ItemPedido item = itens.get(i);

            if (item.getProduto().getId() == produtoId) {
                if (quantidade == 0) {
                    itens.remove(i);
                } else {
                    item.alterarQuantidade(quantidade);
                }
                atualizarValorTotal();
                return;
            }
        }
    }

    @Override
    public double getValorTotal() {
        return valorTotalPedido;
    }

    @Override
    public void confirmarCancelamento() {
        if (isPagamentoRegistrado()) {
            creditoGeradoPedido = valorTotalPedido;
        } else {
            creditoGeradoPedido = 0.0;
        }
    }

    @Override
    public double getCreditoGerado() {
        return creditoGeradoPedido;
    }

    private void atualizarValorTotal() {
        double total = 0.0;

        for (ItemPedido item : itens) {
            total = total + item.getSubtotal();
        }

        valorTotalPedido = total;
    }
}
