/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.presenter;

import br.com.estudos.produtos.model.Produto;
import br.com.estudos.produtos.servico.RegraNegocioException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;

public class Formatos {
    private static final Locale BR = Locale.forLanguageTag("pt-BR");
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private Formatos() {
    }

    public static String numero(Double valor) {
        return valor == null ? "" : String.format(BR, "%.2f", valor);
    }

    public static Double lerNumero(String texto, String campo) {
        String valor = texto == null ? "" : texto.trim();
        if (!valor.matches("[+-]?[0-9]+([.,][0-9]+)?")) {
            throw new RegraNegocioException("Informe " + campo + " usando um número, por exemplo 25,50 (sem separador de milhar).");
        }
        try {
            double numero = Double.parseDouble(valor.replace(',', '.'));
        if (Double.isNaN(numero) || Double.isInfinite(numero)) {
            throw new NumberFormatException();
        }
            return numero;
        } catch (NumberFormatException e) {
            throw new RegraNegocioException("Valor inválido para " + campo + ".");
        }
    }

    public static String data(LocalDate data) {
        return data.format(DATA);
    }

    public static String dataHora(LocalDateTime dataHora) {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm"));
    }

    public static LocalDate lerData(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new RegraNegocioException("Informe uma data válida no formato dd/MM/aaaa.");
        }
        try {
            return LocalDate.parse(texto.trim(), DATA);
        } catch (DateTimeParseException e) {
            throw new RegraNegocioException("Informe uma data válida no formato dd/MM/aaaa.");
        }
    }

    public static String[][] produtos(List<Produto> lista) {
        String[][] linhas = new String[lista.size()][5];
        for (int i = 0; i < lista.size(); i++) {
            Produto p = lista.get(i);
            linhas[i] = new String[] {p.getNome(), numero(p.getPrecoCusto()), p.getCategoria().getNome(),
                    numero(p.getMargemAtual()), numero(p.getPrecoVendaAtual())};
        }
        return linhas;
    }
}

