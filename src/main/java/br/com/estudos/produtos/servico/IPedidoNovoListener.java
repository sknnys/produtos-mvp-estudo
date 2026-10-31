package br.com.estudos.produtos.servico;

import br.com.estudos.produtos.model.Pedido;

public interface IPedidoNovoListener {
    void pedidoCriado(Pedido pedido);
}
