package br.com.estudos.produtos.desconto;

import br.com.estudos.produtos.model.DescontoTaxaEntrega;
import br.com.estudos.produtos.model.ItemPedido;
import br.com.estudos.produtos.model.Pedido;

public class FormaDescontoTipoItem implements IFormaDescontoTaxaEntrega {
    @Override
    public boolean seAplica(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            if (getDescontoPorCategoria(item) > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public DescontoTaxaEntrega calcularDesconto(Pedido pedido) {
        double valor = 0.0;

        for (ItemPedido item : pedido.getItens()) {
            valor = valor + getDescontoPorCategoria(item);
        }

        return new DescontoTaxaEntrega("Desconto por tipo de item", valor, false);
    }

    private double getDescontoPorCategoria(ItemPedido item) {
        String categoria = item.getProduto().getCategoria().getNome();

        if ("Alimentação".equals(categoria) || "Alimentacao".equals(categoria)) {
            return 5.0;
        } else if ("Educação".equals(categoria) || "Educacao".equals(categoria)) {
            return 2.0;
        } else if ("Lazer".equals(categoria)) {
            return 1.5;
        }

        return 0.0;
    }
}
