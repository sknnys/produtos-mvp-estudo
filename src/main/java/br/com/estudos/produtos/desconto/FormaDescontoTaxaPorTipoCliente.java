package br.com.estudos.produtos.desconto;

import br.com.estudos.produtos.model.Cliente;
import br.com.estudos.produtos.model.DescontoTaxaEntrega;
import br.com.estudos.produtos.model.Pedido;

public class FormaDescontoTaxaPorTipoCliente implements IFormaDescontoTaxaEntrega {
    @Override
    public boolean seAplica(Pedido pedido) {
        String tipo = pedido.getCliente().getTipo();

        return Cliente.OURO.equals(tipo)
                || Cliente.PRATA.equals(tipo)
                || Cliente.BRONZE.equals(tipo);
    }

    @Override
    public DescontoTaxaEntrega calcularDesconto(Pedido pedido) {
        String tipo = pedido.getCliente().getTipo();
        double valor = 0.0;

        if (Cliente.OURO.equals(tipo)) {
            valor = 3.0;
        } else if (Cliente.PRATA.equals(tipo)) {
            valor = 2.0;
        } else if (Cliente.BRONZE.equals(tipo)) {
            valor = 1.0;
        }

        return new DescontoTaxaEntrega("Desconto por tipo de cliente", valor, false);
    }
}
