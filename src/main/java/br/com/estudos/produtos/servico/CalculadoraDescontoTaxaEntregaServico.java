package br.com.estudos.produtos.servico;

import br.com.estudos.produtos.desconto.IFormaDescontoTaxaEntrega;
import br.com.estudos.produtos.model.DescontoTaxaEntrega;
import br.com.estudos.produtos.model.Pedido;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraDescontoTaxaEntregaServico {
    private List<IFormaDescontoTaxaEntrega> formasDesconto = new ArrayList<>();

    public void adicionarFormaDesconto(IFormaDescontoTaxaEntrega formaDesconto) {
        formasDesconto.add(formaDesconto);
    }

    public List<DescontoTaxaEntrega> calcular(Pedido pedido) {
        pedido.limparDescontosTaxa();

        if (pedido.temCupom()) {
            return pedido.getDescontosTaxa();
        }

        for (IFormaDescontoTaxaEntrega formaDesconto : formasDesconto) {
            if (pedido.atingiuLimiteDescontoTaxa()) {
                break;
            }

            if (formaDesconto.seAplica(pedido)) {
                pedido.aplicarDescontoTaxa(formaDesconto.calcularDesconto(pedido));
            }
        }

        return pedido.getDescontosTaxa();
    }
}
