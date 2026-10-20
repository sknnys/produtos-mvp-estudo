/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.presenter;

import br.com.estudos.produtos.model.Cliente;
import br.com.estudos.produtos.servico.ClienteServico;
import br.com.estudos.produtos.servico.RegraNegocioException;
import br.com.estudos.produtos.view.contrato.IClientesView;
import java.util.ArrayList;
import java.util.List;

public class ClientesPresenter {
    private final IClientesView view;
    private final ClienteServico service;
    private List<Cliente> clientes = new ArrayList<>();
    private Integer idEdicao;
    private boolean editando;

    public ClientesPresenter(IClientesView view, ClienteServico service) {
        this(view, service, false);
    }

    public ClientesPresenter(IClientesView view, ClienteServico service, boolean iniciarInclusao) {
        this.view = view;
        this.service = service;

        view.aoNovo(this::novo);
        view.aoEditar(new Runnable() {
            @Override
            public void run() {
                editar();
            }
        });
        view.aoExcluir(new Runnable() {
            @Override
            public void run() {
                excluir();
            }
        });
        view.aoSalvar(new Runnable() {
            @Override
            public void run() {
                salvar();
            }
        });
        view.aoCancelar(new Runnable() {
            @Override
            public void run() {
                cancelar();
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
        atualizar();
        if (iniciarInclusao) {
            novo();
        }
    }

    private Cliente selecionado() {
        int indice = view.getLinha();
        if (indice >= 0 && indice < clientes.size()) {
            return clientes.get(indice);
        }
        return null;
    }

    private void atualizar() {
        clientes = service.listarTodos();
        String[][] linhas = new String[clientes.size()][5];

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            linhas[i] = new String[]{
                cliente.getNome(),
                cliente.getCidade(),
                cliente.getUf(),
                cliente.getTipo(),
                Formatos.numero(cliente.getTotalCompras())
            };
        }

        view.mostrarClientes(linhas);
        int indice = 0;

        if (idEdicao != null) {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getId() == idEdicao) {
                    indice = i;
                    break;
                }
            }
        }

        view.selecionarLinha(clientes.isEmpty() ? -1 : indice);
        selecionar();
    }

    private void selecionar() {
        if (editando) {
            return;
        }

        Cliente cliente = selecionado();

        if (cliente == null) {
            view.mostrarDados("", "", "", "", "", "", "");
            view.definirModo(false, false, "Visualização");
            return;
        }

        view.mostrarDados(
                cliente.getNome(),
                cliente.getLogradouro(),
                cliente.getBairro(),
                cliente.getCidade(),
                cliente.getUf(),
                cliente.getTipo(),
                Formatos.numero(cliente.getTotalCompras())
        );
        view.definirModo(false, true, "Visualização");
    }

    private void novo() {
        idEdicao = null;
        editando = true;
        view.mostrarDados("", "", "", "", "", Cliente.PRATA, Formatos.numero(0.0));
        view.definirModo(true, false, "Inclusão");
    }

    private void editar() {
        Cliente cliente = selecionado();
        if (cliente == null) {
            return;
        }

        idEdicao = cliente.getId();
        editando = true;
        view.definirModo(true, true, "Edição");
    }

    private void salvar() {
        try {
            service.salvar(
                    idEdicao,
                    view.getNome(),
                    view.getLogradouro(),
                    view.getBairro(),
                    view.getCidade(),
                    view.getUf()
            );
            editando = false;
            atualizar();
        } catch (RegraNegocioException e) {
            view.exibirMensagem(e.getMessage());
        }
    }

    private void cancelar() {
        editando = false;
        atualizar();
    }

    private void excluir() {
        Cliente cliente = selecionado();
        if (cliente == null) {
            return;
        }

        if (!view.exibirConfirmacao("Excluir o cliente " + cliente.getNome() + "?")) {
            return;
        }

        try {
            service.excluir(cliente.getId());
            atualizar();
        } catch (RegraNegocioException e) {
            view.exibirMensagem(e.getMessage());
        }
    }
}
