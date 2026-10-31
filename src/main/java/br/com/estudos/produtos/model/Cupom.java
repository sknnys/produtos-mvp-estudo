package br.com.estudos.produtos.model;

import java.time.LocalDateTime;

public class Cupom {
    private String codigo;
    private Double percentualDesconto;
    private LocalDateTime inicioValidade;
    private LocalDateTime fimValidade;

    public Cupom(String codigo, Double percentualDesconto,
            LocalDateTime inicioValidade, LocalDateTime fimValidade) {
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.inicioValidade = inicioValidade;
        this.fimValidade = fimValidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public Double getPercentualDesconto() {
        return percentualDesconto;
    }

    public LocalDateTime getInicioValidade() {
        return inicioValidade;
    }

    public LocalDateTime getFimValidade() {
        return fimValidade;
    }

    public boolean estaValido() {
        LocalDateTime agora = LocalDateTime.now();

        return !agora.isBefore(inicioValidade) && !agora.isAfter(fimValidade);
    }
}
