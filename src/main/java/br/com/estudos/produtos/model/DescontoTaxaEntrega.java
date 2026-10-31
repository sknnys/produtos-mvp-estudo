package br.com.estudos.produtos.model;

public class DescontoTaxaEntrega {
    private String descricao;
    private double valor;
    private boolean parcial;

    public DescontoTaxaEntrega(String descricao, double valor, boolean parcial) {
        this.descricao = descricao;
        this.valor = valor;
        this.parcial = parcial;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public boolean isParcial() {
        return parcial;
    }
}
