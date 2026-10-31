/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos;

import br.com.estudos.produtos.presenter.BuscaPresenter;
import br.com.estudos.produtos.desconto.FormaDescontoTaxaPorBairro;
import br.com.estudos.produtos.desconto.FormaDescontoTaxaPorTipoCliente;
import br.com.estudos.produtos.desconto.FormaDescontoTipoItem;
import br.com.estudos.produtos.desconto.FormaDescontoValorPedido;
import br.com.estudos.produtos.presenter.CalculoPresenter;
import br.com.estudos.produtos.presenter.CategoriasPresenter;
import br.com.estudos.produtos.presenter.ClientesPresenter;
import br.com.estudos.produtos.presenter.HistoricoPresenter;
import br.com.estudos.produtos.presenter.INavegacao;
import br.com.estudos.produtos.presenter.IUsuariosNavegacao;
import br.com.estudos.produtos.presenter.ILoginNavegacao;
import br.com.estudos.produtos.presenter.LoginPresenter;
import br.com.estudos.produtos.presenter.PrincipalPresenter;
import br.com.estudos.produtos.presenter.PedidosPresenter;
import br.com.estudos.produtos.presenter.PagamentoPresenter;
import br.com.estudos.produtos.presenter.ProdutoDetalhePresenter;
import br.com.estudos.produtos.presenter.ProdutoEdicaoPresenter;
import br.com.estudos.produtos.presenter.UsuariosPresenter;
import br.com.estudos.produtos.repositorio.ICategoriaRepository;
import br.com.estudos.produtos.repositorio.IClienteRepository;
import br.com.estudos.produtos.repositorio.ICupomRepository;
import br.com.estudos.produtos.repositorio.IHistoricoPrecoRepository;
import br.com.estudos.produtos.repositorio.IProdutoRepository;
import br.com.estudos.produtos.repositorio.IUsuarioRepository;
import br.com.estudos.produtos.repositorio.memoria.PedidoRepositoryMemoria;
import br.com.estudos.produtos.repositorio.memoria.CategoriaRepositoryMemoria;
import br.com.estudos.produtos.repositorio.memoria.ClienteRepositoryMemoria;
import br.com.estudos.produtos.repositorio.memoria.CupomRepositoryMemoria;
import br.com.estudos.produtos.repositorio.memoria.HistoricoPrecoRepositoryMemoria;
import br.com.estudos.produtos.repositorio.memoria.ProdutoRepositoryMemoria;
import br.com.estudos.produtos.repositorio.memoria.UsuarioRepositoryMemoria;
import br.com.estudos.produtos.seeder.Seeder;
import br.com.estudos.produtos.servico.CalculoPrecoServico;
import br.com.estudos.produtos.servico.AutenticacaoServico;
import br.com.estudos.produtos.servico.CategoriaServico;
import br.com.estudos.produtos.servico.ClienteServico;
import br.com.estudos.produtos.servico.CupomServico;
import br.com.estudos.produtos.servico.CalculadoraDescontoTaxaEntregaServico;
import br.com.estudos.produtos.servico.PagamentoServico;
import br.com.estudos.produtos.servico.SimuladorFalhaPagamentoService;
import br.com.estudos.produtos.servico.ProdutoServico;
import br.com.estudos.produtos.servico.PedidoServico;
import br.com.estudos.produtos.servico.UsuarioServico;
import br.com.estudos.produtos.servico.IPedidoNovoListener;
import br.com.estudos.produtos.view.BuscaDialog;
import br.com.estudos.produtos.view.CalculoDialog;
import br.com.estudos.produtos.view.CategoriasDialog;
import br.com.estudos.produtos.view.ClientesDialog;
import br.com.estudos.produtos.view.HistoricoDialog;
import br.com.estudos.produtos.view.LoginFrame;
import br.com.estudos.produtos.view.PrincipalFrame;
import br.com.estudos.produtos.view.PedidosDialog;
import br.com.estudos.produtos.view.PagamentoDialog;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import br.projeto.painelpedidos.aplicacao.PainelPedidoFacade;
import br.projeto.painelpedidos.configuracao.ConfiguracaoPainelPedido;
import br.projeto.painelpedidos.configuracao.LeitorConfiguracaoPainelPedido;
import br.projeto.painelpedidos.listener.IConfirmarPagamentoListener;
import br.projeto.painelpedidos.listener.ICriarNovoPedidoListener;
import br.projeto.painelpedidos.listener.IVisualizarPedidoListener;
import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.som.SomTomStrategy;
import br.projeto.painelpedidos.tempo.RelogioServiceSistema;
import java.util.ArrayList;
import java.util.List;
import br.com.estudos.produtos.view.ProdutoDetalheDialog;
import br.com.estudos.produtos.view.ProdutoEdicaoDialog;
import br.com.estudos.produtos.view.UsuariosDialog;

public class Aplicacao implements INavegacao, ILoginNavegacao {
    private final ICategoriaRepository categoriaRepository = new CategoriaRepositoryMemoria();
    private final IProdutoRepository produtoRepository = new ProdutoRepositoryMemoria();
    private final IHistoricoPrecoRepository historicoPrecoRepository = new HistoricoPrecoRepositoryMemoria();
    private final IClienteRepository clienteRepository = new ClienteRepositoryMemoria();
    private final CategoriaServico categoriaServico = new CategoriaServico(categoriaRepository, produtoRepository);
    private final ProdutoServico produtoServico = new ProdutoServico(produtoRepository, categoriaRepository);
    private final CalculoPrecoServico calculoServico = new CalculoPrecoServico(produtoRepository, historicoPrecoRepository);
    private final ClienteServico clienteServico = new ClienteServico(clienteRepository);
    private final IUsuarioRepository usuarioRepository = new UsuarioRepositoryMemoria();
    private final UsuarioServico usuarioServico = new UsuarioServico(usuarioRepository);
    private final AutenticacaoServico autenticacaoServico = new AutenticacaoServico(usuarioRepository);
    private final ICupomRepository cupomRepository = new CupomRepositoryMemoria();
    private final CupomServico cupomServico = new CupomServico(cupomRepository);
    private final IPedidoRepository pedidoRepository = new PedidoRepositoryMemoria();
    private final CalculadoraDescontoTaxaEntregaServico calculadoraDescontoTaxa
            = criarCalculadoraDescontoTaxa();
    private final PagamentoServico pagamentoServico = new PagamentoServico(
            SimuladorFalhaPagamentoService.getInstancia());
    private final PedidoServico pedidoServico = new PedidoServico(pedidoRepository,
            cupomServico, calculadoraDescontoTaxa, pagamentoServico);
    private PainelPedidoFacade painelPedido;

    public void iniciar() {
        new Seeder(categoriaServico, produtoServico, calculoServico, usuarioServico,
                clienteServico, cupomServico).executar();
        abrirLogin();
    }

    private CalculadoraDescontoTaxaEntregaServico criarCalculadoraDescontoTaxa() {
        CalculadoraDescontoTaxaEntregaServico calculadora
                = new CalculadoraDescontoTaxaEntregaServico();
        calculadora.adicionarFormaDesconto(new FormaDescontoTaxaPorBairro());
        calculadora.adicionarFormaDesconto(new FormaDescontoTaxaPorTipoCliente());
        calculadora.adicionarFormaDesconto(new FormaDescontoTipoItem());
        calculadora.adicionarFormaDesconto(new FormaDescontoValorPedido());
        return calculadora;
    }

    private void abrirLogin() {
        LoginFrame view = new LoginFrame();
        new LoginPresenter(view, autenticacaoServico, this);
        view.exibir();
    }

    @Override
    public void abrirPrincipal(br.com.estudos.produtos.model.Usuario usuario) {
        PrincipalFrame view = new PrincipalFrame();
        new PrincipalPresenter(view, this, usuario);
        view.exibir();
    }

    @Override
    public void sair() {
        abrirLogin();
    }


    @Override
    public void incluirProduto() {
        ProdutoEdicaoDialog view = new ProdutoEdicaoDialog();
        new ProdutoEdicaoPresenter(view, produtoServico, categoriaServico, null);
        view.exibir();
    }


    @Override
    public void buscarProdutos() {
        BuscaDialog view = new BuscaDialog();
        new BuscaPresenter(view, produtoServico, this);
        view.exibir();
    }


    @Override
    public void categorias() {
        CategoriasDialog view = new CategoriasDialog();
        new CategoriasPresenter(view, categoriaServico);
        view.exibir();
    }

    @Override
    public void clientes() {
        ClientesDialog view = new ClientesDialog();
        new ClientesPresenter(view, clienteServico);
        view.exibir();
    }

    @Override
    public void pedidos(br.com.estudos.produtos.model.Usuario usuario) {
        PedidosDialog view = new PedidosDialog();
        new PedidosPresenter(view, pedidoServico, clienteServico, produtoServico, usuario, this);
        view.exibir();
    }

    @Override
    public void painelPedidos(final br.com.estudos.produtos.model.Usuario usuario) {
        if (painelPedido == null) {
            ConfiguracaoPainelPedido configuracao = new LeitorConfiguracaoPainelPedido(
                    ".", "painel_pedidos.env").ler();
            painelPedido = new PainelPedidoFacade(pedidoRepository, configuracao,
                    new SomTomStrategy(880, 200), new RelogioServiceSistema());
            painelPedido.setVisualizarPedidoListener(new IVisualizarPedidoListener() {
                @Override
                public void visualizarPedido(PedidoAbstract pedido) {
                    Aplicacao.this.visualizarPedido(pedido.getIdentificador(), usuario);
                }
            });
            painelPedido.setConfirmarPagamentoListener(new IConfirmarPagamentoListener() {
                @Override
                public void confirmarPagamento(PedidoAbstract pedido) {
                    Aplicacao.this.pagamento(pedido.getIdentificador(), usuario);
                }
            });
            painelPedido.setCriarNovoPedidoListener(new ICriarNovoPedidoListener() {
                @Override
                public List<PedidoAbstract> criarNovoPedido() {
                    Aplicacao.this.pedidos(usuario);
                    return new ArrayList<>();
                }
            });
            pedidoServico.definirPedidoNovoListener(new IPedidoNovoListener() {
                @Override
                public void pedidoCriado(br.com.estudos.produtos.model.Pedido pedido) {
                    List<PedidoAbstract> pedidos = new ArrayList<>();
                    pedidos.add(pedido);
                    painelPedido.receberNovosPedidos(pedidos);
                }
            });
        }

        if (!painelPedido.iniciar()) {
            return;
        }
    }

    @Override
    public void pagamento(String identificadorPedido, br.com.estudos.produtos.model.Usuario usuario) {
        PagamentoDialog view = new PagamentoDialog();
        new PagamentoPresenter(view, pedidoServico, this, usuario, identificadorPedido);
        view.exibir();
    }

    @Override
    public void visualizarPedido(String identificadorPedido, br.com.estudos.produtos.model.Usuario usuario) {
        PedidosDialog view = new PedidosDialog();
        new PedidosPresenter(view, pedidoServico, clienteServico, produtoServico,
                usuario, this, identificadorPedido, true);
        view.exibir();
    }

    @Override
    public void usuarios(br.com.estudos.produtos.model.Usuario usuario) {
        UsuariosDialog view = new UsuariosDialog();
        new UsuariosPresenter(view, usuarioServico, clienteServico, usuario, new IUsuariosNavegacao() {
            @Override
            public void incluirCliente() {
                ClientesDialog clientesView = new ClientesDialog();
                new ClientesPresenter(clientesView, clienteServico, true);
                clientesView.exibir();
            }
        });
        view.exibir();
    }


    @Override
    public void calcular() {
        CalculoDialog view = new CalculoDialog();
        new CalculoPresenter(view, calculoServico);
        view.exibir();
    }


    @Override
    public void visualizarProduto(int id) {
        ProdutoDetalheDialog view = new ProdutoDetalheDialog();
        new ProdutoDetalhePresenter(view, produtoServico, this, id);
        view.exibir();
    }


    @Override
    public void editarProduto(int id) {
        ProdutoEdicaoDialog view = new ProdutoEdicaoDialog();
        new ProdutoEdicaoPresenter(view, produtoServico, categoriaServico, id);
        view.exibir();
    }


    @Override
    public void historicoProduto(int id) {
        HistoricoDialog view = new HistoricoDialog();
        new HistoricoPresenter(view, produtoServico, historicoPrecoRepository, id);
        view.exibir();
    }
}
