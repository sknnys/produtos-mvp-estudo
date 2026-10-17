/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.model;

import br.projeto.painelpedidos.model.PedidoAbstract;
import java.time.LocalDateTime;

public class Pedido extends PedidoAbstract {

    public Pedido(String identificador, LocalDateTime dataHora,
            String nomeCliente, String enderecoEntrega, double valorTotal) {
        super(identificador, dataHora, nomeCliente, enderecoEntrega, valorTotal);
    }

    @Override
    public Pedido criarCopia(String novoIdentificador, LocalDateTime novaDataHora) {
        return new Pedido(novoIdentificador, novaDataHora,
                getNomeCliente(), getEnderecoEntrega(), getValorTotal());
    }
}
