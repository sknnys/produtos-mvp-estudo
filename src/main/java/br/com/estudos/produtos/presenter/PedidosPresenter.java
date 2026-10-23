/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.presenter;

import br.com.estudos.produtos.model.Pedido;
import br.com.estudos.produtos.model.Cliente;
import br.com.estudos.produtos.model.Usuario;
import br.com.estudos.produtos.servico.ClienteServico;
import br.com.estudos.produtos.servico.PedidoServico;
import br.com.estudos.produtos.servico.RegraNegocioException;
import br.com.estudos.produtos.view.contrato.IPedidosView;
import java.util.ArrayList;
import java.util.List;

public class PedidosPresenter {
    private IPedidosView view;
    private PedidoServico service;
    private ClienteServico clienteServico;
    private Usuario usuario;
    private List<Pedido> pedidos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private String identificadorSelecionado;
    private boolean mostrandoPedido;

    public PedidosPresenter(IPedidosView view, PedidoServico service,
            ClienteServico clienteServico, Usuario usuario) {
        this.view = view;
        this.service = service;
        this.clienteServico = clienteServico;
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
