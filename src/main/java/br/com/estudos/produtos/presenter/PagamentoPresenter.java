package br.com.estudos.produtos.presenter;

import br.com.estudos.produtos.model.ItemPedido;
import br.com.estudos.produtos.model.Pedido;
import br.com.estudos.produtos.model.Usuario;
import br.com.estudos.produtos.servico.PedidoServico;
import br.com.estudos.produtos.servico.RegraNegocioException;
import br.com.estudos.produtos.view.contrato.IPagamentoView;
import java.util.List;

public class PagamentoPresenter {
    private IPagamentoView view;
    private PedidoServico service;
    private INavegacao navegacao;
    private Usuario usuario;
    private String identificadorPedido;
    private boolean finalizado;

    public PagamentoPresenter(IPagamentoView view, PedidoServico service,
            INavegacao navegacao, Usuario usuario, String identificadorPedido) {
        this.view = view;
        this.service = service;
        this.navegacao = navegacao;
        this.usuario = usuario;
        this.identificadorPedido = identificadorPedido;

        view.aoAlterarForma(new Runnable() {
            @Override
            public void run() {
                view.mostrarCamposForma(view.getFormaPagamento());
            }
        });
        view.aoFinalizar(new Runnable() {
            @Override
            public void run() {
                finalizar();
            }
        });
        view.aoVisualizarPedido(new Runnable() {
            @Override
            public void run() {
                visualizarPedido();
            }
        });
        view.aoFechar(new Runnable() {
            @Override
            public void run() {
                view.fechar();
            }
        });
        carregar();
    }

    private void carregar() {
        Pedido pedido = service.consultar(identificadorPedido);
        mostrarPedido(pedido);
        view.mostrarCamposForma(view.getFormaPagamento());
        view.definirFinalizado(pedido.isPagamentoRegistrado());

        if (pedido.isPagamentoRegistrado()) {
            mostrarConfirmacao(pedido);
            finalizado = true;
        }
    }

    private void finalizar() {
        try {
            service.registrarPagamento(identificadorPedido, view.getFormaPagamento());
            Pedido pedido = service.consultar(identificadorPedido);
            finalizado = true;
            view.definirFinalizado(true);
            mostrarPedido(pedido);
            mostrarConfirmacao(pedido);
        } catch (RegraNegocioException e) {
            view.exibirMensagem(e.getMessage());
        } catch (RuntimeException e) {
            view.exibirMensagem(e.getMessage());
        }
    }

    private void visualizarPedido() {
        if (finalizado) {
            navegacao.visualizarPedido(identificadorPedido, usuario);
        }
    }

    private void mostrarPedido(Pedido pedido) {
        String[][] linhas = montarItens(pedido.getItens());

        view.mostrarResumo(
                pedido.getIdentificador(),
                pedido.getNomeCliente(),
                Formatos.dataHora(pedido.getDataHora()),
                Formatos.numero(pedido.getValorTotal()),
                Formatos.numero(pedido.getTaxaEntregaFinal()),
                Formatos.numero(pedido.getValorDescontoCupom()),
                Formatos.numero(pedido.getTotalPagar())
        );
        view.mostrarItens(linhas);
    }

    private void mostrarConfirmacao(Pedido pedido) {
        String texto = "Pagamento confirmado\n"
                + "Pedido: " + pedido.getIdentificador() + "\n"
                + "Situação: " + pedido.getSituacao() + "\n"
                + "Forma: " + pedido.getFormaPagamento() + "\n"
                + "Data e hora: " + Formatos.dataHora(pedido.getDataHoraPagamento());
        view.mostrarConfirmacao(texto);
    }

    private String[][] montarItens(List<ItemPedido> itens) {
        String[][] linhas = new String[itens.size()][4];

        for (int i = 0; i < itens.size(); i++) {
            ItemPedido item = itens.get(i);
            linhas[i] = new String[]{
                item.getProduto().getNome(),
                Formatos.numero(item.getProduto().getPrecoVendaAtual()),
                String.valueOf(item.getQuantidade()),
                Formatos.numero(item.getSubtotal())
            };
        }

        return linhas;
    }
}
