package br.com.estudos.produtos.desconto;

import br.com.estudos.produtos.model.DescontoTaxaEntrega;
import br.com.estudos.produtos.model.Pedido;

public class FormaDescontoValorPedido implements IFormaDescontoTaxaEntrega {
    @Override
    public boolean seAplica(Pedido pedido) {
        return pedido.getValorTotal() > 200.0;
    }

    @Override
    public DescontoTaxaEntrega calcularDesconto(Pedido pedido) {
        return new DescontoTaxaEntrega("Desconto por valor do pedido", 5.0, false);
    }
}
