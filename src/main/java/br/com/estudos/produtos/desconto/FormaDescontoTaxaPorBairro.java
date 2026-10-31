package br.com.estudos.produtos.desconto;

import br.com.estudos.produtos.model.DescontoTaxaEntrega;
import br.com.estudos.produtos.model.Pedido;

public class FormaDescontoTaxaPorBairro implements IFormaDescontoTaxaEntrega {
    @Override
    public boolean seAplica(Pedido pedido) {
        String bairro = pedido.getCliente().getBairro();

        return "Centro".equals(bairro)
                || "Bela Vista".equals(bairro)
                || "Cidade Maravilhosa".equals(bairro);
    }

    @Override
    public DescontoTaxaEntrega calcularDesconto(Pedido pedido) {
        String bairro = pedido.getCliente().getBairro();
        double valor = 0.0;

        if ("Centro".equals(bairro)) {
            valor = 2.0;
        } else if ("Bela Vista".equals(bairro)) {
            valor = 3.0;
        } else if ("Cidade Maravilhosa".equals(bairro)) {
            valor = 1.5;
        }

        return new DescontoTaxaEntrega("Desconto por bairro", valor, false);
    }
}
