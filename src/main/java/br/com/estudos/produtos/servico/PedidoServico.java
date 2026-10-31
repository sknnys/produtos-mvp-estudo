/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.servico;

import br.com.estudos.produtos.model.Pedido;
import br.com.estudos.produtos.model.Cliente;
import br.com.estudos.produtos.model.Produto;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoServico {
    private IPedidoRepository pedidoRepository;
    private CupomServico cupomServico;
    private CalculadoraDescontoTaxaEntregaServico calculadoraDescontoTaxa;
    private PagamentoServico pagamentoServico;
    private IPedidoNovoListener pedidoNovoListener;

    public PedidoServico(IPedidoRepository pedidoRepository, CupomServico cupomServico,
            CalculadoraDescontoTaxaEntregaServico calculadoraDescontoTaxa,
            PagamentoServico pagamentoServico) {
        this.pedidoRepository = pedidoRepository;
        this.cupomServico = cupomServico;
        this.calculadoraDescontoTaxa = calculadoraDescontoTaxa;
        this.pagamentoServico = pagamentoServico;
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
        if (pedidoNovoListener != null) {
            pedidoNovoListener.pedidoCriado(pedido);
        }
        return pedido;
    }

    public void definirPedidoNovoListener(IPedidoNovoListener pedidoNovoListener) {
        this.pedidoNovoListener = pedidoNovoListener;
    }

    public void atualizarEndereco(String identificador, String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new RegraNegocioException("Informe o endereço de entrega.");
        }

        Pedido pedido = consultar(identificador);
        pedido.atualizarEndereco(endereco.trim());
        pedidoRepository.salvar(pedido);
    }

    public void adicionarProduto(String identificador, Produto produto) {
        if (produto == null) {
            throw new RegraNegocioException("Selecione um produto.");
        }

        if (produto.getPrecoVendaAtual() == null) {
            throw new RegraNegocioException("Calcule o preço de venda do produto antes de adicionar.");
        }

        Pedido pedido = consultar(identificador);
        pedido.adicionarItem(produto, 1);
        pedidoRepository.salvar(pedido);
    }

    public void alterarQuantidade(String identificador, int produtoId, int quantidade) {
        if (quantidade < 0) {
            throw new RegraNegocioException("A quantidade não pode ser negativa.");
        }

        Pedido pedido = consultar(identificador);
        pedido.alterarQuantidadeItem(produtoId, quantidade);
        pedidoRepository.salvar(pedido);
    }

    public void aplicarCupom(String identificador, String codigo) {
        Pedido pedido = consultar(identificador);
        cupomServico.aplicar(pedido, codigo);
        pedidoRepository.salvar(pedido);
    }

    public void removerCupom(String identificador) {
        Pedido pedido = consultar(identificador);
        pedido.removerCupom();
        pedidoRepository.salvar(pedido);
    }

    public void calcularValores(String identificador) {
        Pedido pedido = consultar(identificador);

        if (pedido.getItens().isEmpty()) {
            throw new RegraNegocioException("Inclua pelo menos um item no pedido.");
        }

        if (pedido.temCupom()) {
            pedido.calcularComCupom();
        } else {
            calculadoraDescontoTaxa.calcular(pedido);
            pedido.calcularSemCupom();
        }

        pedidoRepository.salvar(pedido);
    }

    public void registrarPagamento(String identificador, String formaPagamento) {
        Pedido pedido = consultar(identificador);
        pagamentoServico.finalizar(pedido, formaPagamento);
        pedidoRepository.salvar(pedido);
    }

    public void prepararPagamento(String identificador) {
        Pedido pedido = consultar(identificador);

        if (!pedido.isValoresCalculados()) {
            throw new RegraNegocioException("Calcule os valores antes de pagar o pedido.");
        }

        if (!pedido.estaNovo()) {
            throw new RegraNegocioException("O pagamento só pode ser iniciado em pedido Novo.");
        }

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
