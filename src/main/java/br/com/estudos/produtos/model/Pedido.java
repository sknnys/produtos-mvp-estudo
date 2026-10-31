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
    public static final double TAXA_ENTREGA_PADRAO = 10.0;
    public static final double LIMITE_DESCONTO_TAXA = 10.0;

    private Cliente cliente;
    private String enderecoPedido;
    private List<ItemPedido> itens = new ArrayList<>();
    private double valorTotalPedido;
    private double creditoGeradoPedido;
    private String codigoCupom;
    private Double percentualCupom;
    private double valorDescontoCupom;
    private double totalPagar;
    private boolean valoresCalculados;
    private List<DescontoTaxaEntrega> descontosTaxa = new ArrayList<>();
    private LocalDateTime dataHoraPagamento;

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

    @Override
    public String getEnderecoEntrega() {
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
                invalidarCalculo();
                return;
            }
        }

        itens.add(new ItemPedido(produto, quantidade));
        atualizarValorTotal();
        invalidarCalculo();
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
                invalidarCalculo();
                return;
            }
        }
    }

    public void atualizarEndereco(String enderecoPedido) {
        this.enderecoPedido = enderecoPedido;
        invalidarCalculo();
    }

    @Override
    public double getValorTotal() {
        return valorTotalPedido;
    }

    public boolean temCupom() {
        return codigoCupom != null;
    }

    public String getCodigoCupom() {
        return codigoCupom;
    }

    public Double getPercentualCupom() {
        return percentualCupom;
    }

    public double getValorDescontoCupom() {
        return valorDescontoCupom;
    }

    public double getTotalPagar() {
        return totalPagar;
    }

    public boolean isValoresCalculados() {
        return valoresCalculados;
    }

    public LocalDateTime getDataHoraPagamento() {
        return dataHoraPagamento;
    }

    @Override
    public void registrarPagamento(String formaPagamento, LocalDateTime quando) {
        super.registrarPagamento(formaPagamento, quando);
        dataHoraPagamento = quando;
    }

    public void aplicarCupom(String codigo, Double percentual) {
        codigoCupom = codigo;
        percentualCupom = percentual;
        limparDescontosTaxa();
        invalidarCalculo();
    }

    public void calcularSemCupom() {
        valorDescontoCupom = 0.0;
        totalPagar = valorTotalPedido + getTaxaEntregaFinal();
        valoresCalculados = true;
    }

    public void calcularComCupom() {
        double valorComTaxa = valorTotalPedido + TAXA_ENTREGA_PADRAO;

        valorDescontoCupom = valorComTaxa * percentualCupom / 100;
        totalPagar = valorComTaxa - valorDescontoCupom;
        valoresCalculados = true;
    }

    public void removerCupom() {
        codigoCupom = null;
        percentualCupom = null;
        valorDescontoCupom = 0.0;
        invalidarCalculo();
    }

    public double getTaxaEntrega() {
        return TAXA_ENTREGA_PADRAO;
    }

    public List<DescontoTaxaEntrega> getDescontosTaxa() {
        return new ArrayList<>(descontosTaxa);
    }

    public double getDescontoTaxaTotal() {
        double total = 0.0;

        for (DescontoTaxaEntrega desconto : descontosTaxa) {
            total = total + desconto.getValor();
        }

        return total;
    }

    public double getTaxaEntregaFinal() {
        return TAXA_ENTREGA_PADRAO - getDescontoTaxaTotal();
    }

    public boolean atingiuLimiteDescontoTaxa() {
        return getDescontoTaxaTotal() >= LIMITE_DESCONTO_TAXA;
    }

    public void aplicarDescontoTaxa(DescontoTaxaEntrega desconto) {
        if (desconto == null || desconto.getValor() <= 0
                || temCupom() || atingiuLimiteDescontoTaxa()) {
            return;
        }

        double restante = LIMITE_DESCONTO_TAXA - getDescontoTaxaTotal();

        if (desconto.getValor() <= restante) {
            descontosTaxa.add(desconto);
        } else {
            descontosTaxa.add(new DescontoTaxaEntrega(
                    desconto.getDescricao() + " (parcial)", restante, true));
        }
    }

    public void limparDescontosTaxa() {
        descontosTaxa.clear();
    }

    @Override
    public void confirmarCancelamento() {
        if (isPagamentoRegistrado()) {
            creditoGeradoPedido = totalPagar;
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

    private void invalidarCalculo() {
        valoresCalculados = false;
        valorDescontoCupom = 0.0;
        totalPagar = 0.0;
        limparDescontosTaxa();
    }

}
