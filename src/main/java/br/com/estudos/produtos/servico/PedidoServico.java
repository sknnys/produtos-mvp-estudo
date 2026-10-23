/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.servico;

import br.com.estudos.produtos.model.Pedido;
import br.com.estudos.produtos.model.Cliente;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoServico {
    private IPedidoRepository pedidoRepository;

    public PedidoServico(IPedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido consultar(String identificador) {
        for (PedidoAbstract pedido : pedidoRepository.buscarTodos()) {
            if (pedido.getIdentificador().equals(identificador)) {
                return (Pedido) pedido;
            }
        }

        throw new RegraNegocioException("Pedido não encontrado.");
    }

    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();

        for (PedidoAbstract pedido : pedidoRepository.buscarTodos()) {
            pedidos.add((Pedido) pedido);
        }

        return pedidos;
    }

    public Pedido criarPedido(Cliente cliente, String endereco) {
        if (cliente == null) {
            throw new RegraNegocioException("Selecione um cliente.");
        }

        String enderecoPedido = endereco;

        if (enderecoPedido == null || enderecoPedido.trim().isEmpty()) {
            enderecoPedido = montarEndereco(cliente);
        }

        Pedido pedido = new Pedido(
                pedidoRepository.gerarProximoIdentificador(),
                LocalDateTime.now(),
                cliente,
                enderecoPedido.trim()
        );
        pedidoRepository.salvar(pedido);
        return pedido;
    }

    public void atualizarEndereco(String identificador, String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new RegraNegocioException("Informe o endereço de entrega.");
        }

        Pedido pedido = consultar(identificador);
        pedido.atualizarEndereco(endereco.trim());
        pedidoRepository.salvar(pedido);
    }

    public void cancelar(String identificador) {
        Pedido pedido = consultar(identificador);

        if (pedido.estaCancelado()) {
            throw new RegraNegocioException("Pedido já está cancelado.");
        }

        pedido.cancelar(LocalDateTime.now());
        pedidoRepository.salvar(pedido);
    }

    public Pedido copiarPedidoCancelado(String identificador) {
        Pedido pedido = consultar(identificador);

        if (!pedido.estaCancelado()) {
            throw new RegraNegocioException("A cópia só pode ser feita de pedido cancelado.");
        }

        Pedido copia = (Pedido) pedido.copiarComoNovo(
                pedidoRepository.gerarProximoIdentificador(), LocalDateTime.now());

        pedidoRepository.salvar(copia);
        return copia;
    }

    private String montarEndereco(Cliente cliente) {
        return cliente.getLogradouro() + ", " + cliente.getBairro()
                + " - " + cliente.getCidade() + "/" + cliente.getUf();
    }
}
