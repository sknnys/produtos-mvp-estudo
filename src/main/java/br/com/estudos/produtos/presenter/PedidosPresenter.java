/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.presenter;

import br.com.estudos.produtos.model.Pedido;
import br.com.estudos.produtos.model.Cliente;
import br.com.estudos.produtos.model.ItemPedido;
import br.com.estudos.produtos.model.Produto;
import br.com.estudos.produtos.model.Usuario;
import br.com.estudos.produtos.servico.ClienteServico;
import br.com.estudos.produtos.servico.PedidoServico;
import br.com.estudos.produtos.servico.ProdutoServico;
import br.com.estudos.produtos.servico.RegraNegocioException;
import br.com.estudos.produtos.view.contrato.IPedidosView;
import java.util.ArrayList;
import java.util.List;

public class PedidosPresenter {
    private IPedidosView view;
    private PedidoServico service;
    private ClienteServico clienteServico;
    private ProdutoServico produtoServico;
    private Usuario usuario;
    private List<Pedido> pedidos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private String identificadorSelecionado;
    private boolean mostrandoPedido;

    public PedidosPresenter(IPedidosView view, PedidoServico service,
            ClienteServico clienteServico, ProdutoServico produtoServico,
            Usuario usuario) {
        this.view = view;
        this.service = service;
        this.clienteServico = clienteServico;
        this.produtoServico = produtoServico;
        this.usuario = usuario;

        view.aoNovo(new Runnable() {
            @Override
            public void run() {
                novo();
            }
        });
        view.aoSalvarEndereco(new Runnable() {
            @Override
            public void run() {
                salvarEndereco();
            }
        });
        view.aoSelecionarCliente(new Runnable() {
            @Override
            public void run() {
                carregarEnderecoCliente();
            }
        });
        view.aoAdicionarProduto(new Runnable() {
            @Override
            public void run() {
                adicionarProduto();
            }
        });
        view.aoAlterarQuantidade(new Runnable() {
            @Override
            public void run() {
                alterarQuantidade();
            }
        });
        view.aoSelecionarItem(new Runnable() {
            @Override
            public void run() {
                selecionarItem();
            }
        });
        view.aoSelecionar(new Runnable() {
            @Override
            public void run() {
                selecionar();
            }
        });
        view.aoFechar(new Runnable() {
            @Override
            public void run() {
                view.fechar();
            }
        });
        carregarClientes();
        carregarProdutos();
        atualizar();
    }

    private void carregarClientes() {
        clientes = clienteServico.listarTodos();
        String[] nomes = new String[clientes.size()];

        for (int i = 0; i < clientes.size(); i++) {
            nomes[i] = clientes.get(i).getNome();
        }

        view.mostrarClientes(nomes);

        if (Usuario.CLIENTE.equals(usuario.getPerfil())) {
            Cliente cliente = usuario.getCliente();

            if (cliente != null) {
                view.selecionarCliente(cliente.getNome());
                view.definirClienteFixo(true);
                view.mostrarEndereco(montarEndereco(cliente));
            }
        }
    }

    private void carregarProdutos() {
        produtos = produtoServico.listarTodos();
        String[] nomes = new String[produtos.size()];

        for (int i = 0; i < produtos.size(); i++) {
            nomes[i] = produtos.get(i).getNome();
        }

        view.mostrarProdutos(nomes);
    }

    private void atualizar() {
        pedidos = service.listarTodos();
        String[][] linhas = new String[pedidos.size()][4];

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedido = pedidos.get(i);
            linhas[i] = new String[]{
                pedido.getIdentificador(),
                pedido.getNomeCliente(),
                pedido.getSituacao(),
                Formatos.numero(pedido.getValorTotal())
            };
        }

        view.mostrarPedidos(linhas);
        int indice = localizarSelecionado();
        view.selecionarLinha(indice);
        selecionar();
    }

    private void selecionar() {
        int indice = view.getLinha();

        if (indice < 0 || indice >= pedidos.size()) {
            view.mostrarDados("", "", "", "", "");
            view.mostrarItens(new String[0][4]);
            view.mostrarQuantidade("");
            return;
        }

        Pedido pedido = pedidos.get(indice);
        identificadorSelecionado = pedido.getIdentificador();
        mostrandoPedido = true;
        view.mostrarDados(
                pedido.getIdentificador(),
                pedido.getNomeCliente(),
                pedido.getSituacao(),
                Formatos.numero(pedido.getValorTotal()),
                pedido.getEnderecoPedido()
        );
        mostrandoPedido = false;
        mostrarItens(pedido);
    }

    private void novo() {
        try {
            Cliente cliente = clienteSelecionado();
            Pedido pedido = service.criarPedido(cliente, view.getEndereco());
            identificadorSelecionado = pedido.getIdentificador();
            atualizar();
        } catch (RegraNegocioException e) {
            view.exibirMensagem(e.getMessage());
        }
    }

    private void salvarEndereco() {
        if (identificadorSelecionado == null) {
            return;
        }

        try {
            service.atualizarEndereco(identificadorSelecionado, view.getEndereco());
            atualizar();
        } catch (RegraNegocioException e) {
            view.exibirMensagem(e.getMessage());
        }
    }

    private void adicionarProduto() {
        if (identificadorSelecionado == null) {
            view.exibirMensagem("Crie ou selecione um pedido.");
            return;
        }

        try {
            Produto produto = produtoSelecionado();
            service.adicionarProduto(identificadorSelecionado, produto);
            atualizar();
        } catch (RegraNegocioException e) {
            view.exibirMensagem(e.getMessage());
        }
    }

    private void alterarQuantidade() {
        Pedido pedido = pedidoSelecionado();
        int indice = view.getLinhaItem();

        if (pedido == null || indice < 0 || indice >= pedido.getItens().size()) {
            return;
        }

        try {
            int quantidade = Integer.parseInt(view.getQuantidade().trim());
            ItemPedido item = pedido.getItens().get(indice);
            service.alterarQuantidade(identificadorSelecionado,
                    item.getProduto().getId(), quantidade);
            atualizar();
        } catch (NumberFormatException e) {
            view.exibirMensagem("Informe uma quantidade inteira.");
        } catch (RegraNegocioException e) {
            view.exibirMensagem(e.getMessage());
        }
    }

    private void selecionarItem() {
        Pedido pedido = pedidoSelecionado();
        int indice = view.getLinhaItem();

        if (pedido == null || indice < 0 || indice >= pedido.getItens().size()) {
            view.mostrarQuantidade("");
            return;
        }

        view.mostrarQuantidade(String.valueOf(pedido.getItens().get(indice).getQuantidade()));
    }

    private void carregarEnderecoCliente() {
        if (mostrandoPedido) {
            return;
        }

        identificadorSelecionado = null;
        Cliente cliente = clienteSelecionado();

        if (cliente != null) {
            view.mostrarEndereco(montarEndereco(cliente));
        }
    }

    private Cliente clienteSelecionado() {
        if (Usuario.CLIENTE.equals(usuario.getPerfil())) {
            return usuario.getCliente();
        }

        String nome = view.getCliente();

        for (Cliente cliente : clientes) {
            if (cliente.getNome().equals(nome)) {
                return cliente;
            }
        }

        return null;
    }

    private Produto produtoSelecionado() {
        String nome = view.getProduto();

        for (Produto produto : produtos) {
            if (produto.getNome().equals(nome)) {
                return produto;
            }
        }

        return null;
    }

    private Pedido pedidoSelecionado() {
        if (identificadorSelecionado == null) {
            return null;
        }

        return service.consultar(identificadorSelecionado);
    }

    private void mostrarItens(Pedido pedido) {
        List<ItemPedido> itens = pedido.getItens();
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

        view.mostrarItens(linhas);
        view.selecionarLinhaItem(itens.isEmpty() ? -1 : 0);
        selecionarItem();
    }

    private int localizarSelecionado() {
        if (identificadorSelecionado == null) {
            return pedidos.isEmpty() ? -1 : 0;
        }

        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getIdentificador().equals(identificadorSelecionado)) {
                return i;
            }
        }

        return pedidos.isEmpty() ? -1 : 0;
    }

    private String montarEndereco(Cliente cliente) {
        return cliente.getLogradouro() + ", " + cliente.getBairro()
                + " - " + cliente.getCidade() + "/" + cliente.getUf();
    }
}
