package br.com.estudos.produtos.desconto;

import br.com.estudos.produtos.model.DescontoTaxaEntrega;
import br.com.estudos.produtos.model.Pedido;

public interface IFormaDescontoTaxaEntrega {
    boolean seAplica(Pedido pedido);
    DescontoTaxaEntrega calcularDesconto(Pedido pedido);
}
