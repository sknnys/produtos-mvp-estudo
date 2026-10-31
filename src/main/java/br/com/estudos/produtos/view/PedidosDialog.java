/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.view;

import br.com.estudos.produtos.view.contrato.IPedidosView;

public class PedidosDialog extends javax.swing.JDialog implements IPedidosView {
    private boolean atualizandoItens;
    private Runnable acaoAlterarQuantidade;

    /**
     * Creates new form PedidosDialog
     */
    public PedidosDialog() {
        super((java.awt.Frame) null, true);
        initComponents();
    }

    @Override
    public void exibir() {
        setVisible(true);
    }

    @Override
    public void fechar() {
        dispose();
    }

    @Override
    public void exibirMensagem(String texto) {
        javax.swing.JOptionPane.showMessageDialog(this, texto, "Atenção", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public boolean exibirConfirmacao(String texto) {
        return javax.swing.JOptionPane.showConfirmDialog(this, texto, "Confirmação",
                javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION;
    }

    @Override
    public int getLinha() {
        return tablePedidos.getSelectedRow();
    }

    @Override
    public String getCliente() {
        Object cliente = boxCliente.getSelectedItem();
        return cliente == null ? "" : cliente.toString();
    }

    @Override
    public String getEndereco() {
        return textAreaEndereco.getText();
    }

    @Override
    public String getProduto() {
        Object produto = boxProduto.getSelectedItem();
        return produto == null ? "" : produto.toString();
    }

    @Override
    public String getQuantidade() {
        int linha = tableItens.getSelectedRow();

        if (linha >= 0) {
            Object quantidade = tableItens.getValueAt(linha, 2);
            return quantidade == null ? "" : quantidade.toString();
        }

        return textFieldQuantidade.getText();
    }

    @Override
    public String getCodigoCupom() {
        return textFieldCupom.getText();
    }

    @Override
    public int getLinhaItem() {
        return tableItens.getSelectedRow();
    }

    @Override
    public void mostrarClientes(String[] clientes) {
        boxCliente.removeAllItems();
        boxCliente.addItem("");

        for (String cliente : clientes) {
            boxCliente.addItem(cliente);
        }
    }

    @Override
    public void mostrarProdutos(String[] produtos) {
        boxProduto.removeAllItems();
        boxProduto.addItem("");

        for (String produto : produtos) {
            boxProduto.addItem(produto);
        }
    }

    @Override
    public void mostrarPedidos(String[][] linhas) {
        Tabelas.preencher(tablePedidos, linhas);
    }

    @Override
    public void mostrarItens(String[][] linhas) {
        atualizandoItens = true;
        tableItens.setModel(new javax.swing.table.DefaultTableModel(linhas,
                new String[]{"Produto", "Preço unitário", "Quantidade", "Subtotal"}) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return coluna == 2;
            }
        });
        tableItens.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        configurarEdicaoQuantidade();
        atualizandoItens = false;
    }

    @Override
    public void mostrarDados(String numero, String data, String cliente,
            String situacao, String valor, String endereco) {
        textFieldNumero.setText(numero);
        textFieldData.setText(data);
        boxCliente.setSelectedItem(cliente);
        textFieldSituacao.setText(situacao);
        textFieldValor.setText(valor);
        textAreaEndereco.setText(endereco);
    }

    @Override
    public void selecionarLinha(int indice) {
        if (indice < 0) {
            tablePedidos.clearSelection();
        } else {
            tablePedidos.setRowSelectionInterval(indice, indice);
        }
    }

    @Override
    public void selecionarLinhaItem(int indice) {
        if (indice < 0) {
            tableItens.clearSelection();
        } else {
            tableItens.setRowSelectionInterval(indice, indice);
        }
    }

    @Override
    public void selecionarCliente(String cliente) {
        boxCliente.setSelectedItem(cliente);
    }

    @Override
    public void definirClienteFixo(boolean fixo) {
        boxCliente.setEnabled(!fixo);
    }

    @Override
    public void mostrarEndereco(String endereco) {
        textAreaEndereco.setText(endereco);
    }

    @Override
    public void mostrarQuantidade(String quantidade) {
        textFieldQuantidade.setText(quantidade);
    }

    @Override
    public void mostrarResumo(String taxaPadrao, String descontosTaxa,
            String taxaFinal, String descontoCupom, String totalPagar,
            String cupom) {
        textFieldTaxaPadrao.setText(taxaPadrao);
        textFieldDescontosTaxa.setText(descontosTaxa);
        textFieldTaxaFinal.setText(taxaFinal);
        textFieldDescontoCupom.setText(descontoCupom);
        textFieldTotalPagar.setText(totalPagar);
        textFieldCupom.setText(cupom);
    }

    @Override
    public void definirDetalhesHabilitado(boolean habilitado) {
        btnDetalhes.setEnabled(habilitado);
    }

    @Override
    public void definirSomenteLeitura(boolean somenteLeitura) {
        boxCliente.setEnabled(!somenteLeitura);
        textAreaEndereco.setEditable(!somenteLeitura);
        boxProduto.setEnabled(!somenteLeitura);
        textFieldQuantidade.setEditable(!somenteLeitura);
        textFieldCupom.setEditable(!somenteLeitura);
        btnNovo.setEnabled(!somenteLeitura);
        btnSalvarEndereco.setEnabled(!somenteLeitura);
        btnAdicionarProduto.setEnabled(!somenteLeitura);
        btnAlterarQuantidade.setEnabled(!somenteLeitura);
        btnAplicarCupom.setEnabled(!somenteLeitura);
        btnRemoverCupom.setEnabled(!somenteLeitura);
        btnCalcularValor.setEnabled(!somenteLeitura);
        btnPagar.setEnabled(!somenteLeitura);
    }

    @Override
    public void aoNovo(Runnable acao) {
        Eventos.adicionarAcao(btnNovo, acao);
    }

    @Override
    public void aoSalvarEndereco(Runnable acao) {
        Eventos.adicionarAcao(btnSalvarEndereco, acao);
    }

    @Override
    public void aoSelecionarCliente(Runnable acao) {
        Eventos.adicionarSelecao(boxCliente, acao);
    }

    @Override
    public void aoAdicionarProduto(Runnable acao) {
        Eventos.adicionarAcao(btnAdicionarProduto, acao);
    }

    @Override
    public void aoAlterarQuantidade(Runnable acao) {
        Eventos.adicionarAcao(btnAlterarQuantidade, acao);
        acaoAlterarQuantidade = acao;
        configurarEdicaoQuantidade();
    }

    private void configurarEdicaoQuantidade() {
        if (acaoAlterarQuantidade == null) {
            return;
        }

        tableItens.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
            @Override
            public void tableChanged(javax.swing.event.TableModelEvent evento) {
                if (!atualizandoItens
                        && evento.getType() == javax.swing.event.TableModelEvent.UPDATE
                        && evento.getColumn() == 2) {
                    tableItens.setRowSelectionInterval(evento.getFirstRow(), evento.getFirstRow());
                    acaoAlterarQuantidade.run();
                }
            }
        });
    }

    @Override
    public void aoSelecionarItem(Runnable acao) {
        Eventos.adicionarSelecao(tableItens, acao);
    }

    @Override
    public void aoSelecionar(Runnable acao) {
        Eventos.adicionarSelecao(tablePedidos, acao);
    }

    @Override
    public void aoAplicarCupom(Runnable acao) {
        Eventos.adicionarAcao(btnAplicarCupom, acao);
    }

    @Override
    public void aoRemoverCupom(Runnable acao) {
        Eventos.adicionarAcao(btnRemoverCupom, acao);
    }

    @Override
    public void aoCalcularValor(Runnable acao) {
        Eventos.adicionarAcao(btnCalcularValor, acao);
    }

    @Override
    public void aoDetalhes(Runnable acao) {
        Eventos.adicionarAcao(btnDetalhes, acao);
    }

    @Override
    public void aoPagar(Runnable acao) {
        Eventos.adicionarAcao(btnPagar, acao);
    }

    @Override
    public void aoFechar(Runnable acao) {
        Eventos.adicionarAcao(btnFechar, acao);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dados = new javax.swing.JPanel();
        labelNumero = new javax.swing.JLabel();
        textFieldNumero = new javax.swing.JTextField();
        labelData = new javax.swing.JLabel();
        textFieldData = new javax.swing.JTextField();
        labelCliente = new javax.swing.JLabel();
        boxCliente = new javax.swing.JComboBox<>();
        labelSituacao = new javax.swing.JLabel();
        textFieldSituacao = new javax.swing.JTextField();
        labelValor = new javax.swing.JLabel();
        textFieldValor = new javax.swing.JTextField();
        labelEndereco = new javax.swing.JLabel();
        jScrollPaneEndereco = new javax.swing.JScrollPane();
        textAreaEndereco = new javax.swing.JTextArea();
        itens = new javax.swing.JPanel();
        labelProduto = new javax.swing.JLabel();
        boxProduto = new javax.swing.JComboBox<>();
        btnAdicionarProduto = new javax.swing.JButton();
        labelQuantidade = new javax.swing.JLabel();
        textFieldQuantidade = new javax.swing.JTextField();
        btnAlterarQuantidade = new javax.swing.JButton();
        jScrollPaneItens = new javax.swing.JScrollPane();
        tableItens = new javax.swing.JTable();
        resumo = new javax.swing.JPanel();
        labelTaxaPadrao = new javax.swing.JLabel();
        textFieldTaxaPadrao = new javax.swing.JTextField();
        labelDescontosTaxa = new javax.swing.JLabel();
        textFieldDescontosTaxa = new javax.swing.JTextField();
        btnDetalhes = new javax.swing.JButton();
        labelTaxaFinal = new javax.swing.JLabel();
        textFieldTaxaFinal = new javax.swing.JTextField();
        labelCupom = new javax.swing.JLabel();
        textFieldCupom = new javax.swing.JTextField();
        btnAplicarCupom = new javax.swing.JButton();
        btnRemoverCupom = new javax.swing.JButton();
        labelDescontoCupom = new javax.swing.JLabel();
        textFieldDescontoCupom = new javax.swing.JTextField();
        labelTotalPagar = new javax.swing.JLabel();
        textFieldTotalPagar = new javax.swing.JTextField();
        btnCalcularValor = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        listagem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePedidos = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnSalvarEndereco = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pedidos");
        setModal(true);
        setPreferredSize(new java.awt.Dimension(1050, 760));

        dados.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do pedido"));

        labelNumero.setText("Número:");
        labelData.setText("Data:");
        labelCliente.setText("Cliente:");
        labelSituacao.setText("Situação:");
        labelValor.setText("Valor total:");
        labelEndereco.setText("Endereço de entrega:");

        textFieldNumero.setEditable(false);
        textFieldData.setEditable(false);
        textFieldSituacao.setEditable(false);
        textFieldValor.setEditable(false);
        textAreaEndereco.setColumns(20);
        textAreaEndereco.setRows(3);
        textAreaEndereco.setLineWrap(true);
        textAreaEndereco.setWrapStyleWord(true);
        jScrollPaneEndereco.setViewportView(textAreaEndereco);
        boxCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));

        javax.swing.GroupLayout dadosLayout = new javax.swing.GroupLayout(dados);
        dados.setLayout(dadosLayout);
        dadosLayout.setHorizontalGroup(
            dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNumero)
                    .addComponent(labelData)
                    .addComponent(labelCliente)
                    .addComponent(labelSituacao)
                    .addComponent(labelValor)
                    .addComponent(labelEndereco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldNumero)
                    .addComponent(textFieldData)
                    .addComponent(boxCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldSituacao)
                    .addComponent(textFieldValor)
                    .addComponent(jScrollPaneEndereco))
                .addContainerGap())
        );
        dadosLayout.setVerticalGroup(
            dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumero)
                    .addComponent(textFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData)
                    .addComponent(textFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCliente)
                    .addComponent(boxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSituacao)
                    .addComponent(textFieldSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelValor)
                    .addComponent(textFieldValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelEndereco)
                    .addComponent(jScrollPaneEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        itens.setBorder(javax.swing.BorderFactory.createTitledBorder("Itens do pedido"));

        labelProduto.setText("Produto:");
        boxProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        btnAdicionarProduto.setText("Adicionar");
        labelQuantidade.setText("Quantidade:");
        btnAlterarQuantidade.setText("Alterar quantidade");

        tableItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Produto", "Preço unitário", "Quantidade", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableItens.setRowHeight(24);
        jScrollPaneItens.setViewportView(tableItens);

        javax.swing.GroupLayout itensLayout = new javax.swing.GroupLayout(itens);
        itens.setLayout(itensLayout);
        itensLayout.setHorizontalGroup(
            itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itensLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boxProduto, 0, 270, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdicionarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelQuantidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAlterarQuantidade)
                .addContainerGap())
            .addGroup(itensLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneItens)
                .addContainerGap())
        );
        itensLayout.setVerticalGroup(
            itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itensLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(itensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelProduto)
                    .addComponent(boxProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionarProduto)
                    .addComponent(labelQuantidade)
                    .addComponent(textFieldQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterarQuantidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneItens, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );

        resumo.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumo do pedido"));

        labelTaxaPadrao.setText("Taxa de entrega:");
        labelDescontosTaxa.setText("Descontos da taxa:");
        btnDetalhes.setText("Detalhes");
        labelTaxaFinal.setText("Taxa final:");
        labelCupom.setText("Cupom:");
        btnAplicarCupom.setText("Aplicar cupom");
        btnRemoverCupom.setText("Remover cupom");
        labelDescontoCupom.setText("Desconto do cupom:");
        labelTotalPagar.setText("Total a pagar:");
        btnCalcularValor.setText("Calcular valor");
        btnPagar.setText("Pagar pedido");

        textFieldTaxaPadrao.setEditable(false);
        textFieldDescontosTaxa.setEditable(false);
        textFieldTaxaFinal.setEditable(false);
        textFieldDescontoCupom.setEditable(false);
        textFieldTotalPagar.setEditable(false);
        btnDetalhes.setEnabled(false);

        javax.swing.GroupLayout resumoLayout = new javax.swing.GroupLayout(resumo);
        resumo.setLayout(resumoLayout);
        resumoLayout.setHorizontalGroup(
            resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTaxaPadrao)
                    .addComponent(labelDescontosTaxa)
                    .addComponent(labelTaxaFinal)
                    .addComponent(labelCupom)
                    .addComponent(labelDescontoCupom)
                    .addComponent(labelTotalPagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldTaxaPadrao)
                    .addGroup(resumoLayout.createSequentialGroup()
                        .addComponent(textFieldDescontosTaxa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDetalhes))
                    .addComponent(textFieldTaxaFinal)
                    .addGroup(resumoLayout.createSequentialGroup()
                        .addComponent(textFieldCupom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAplicarCupom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoverCupom))
                    .addComponent(textFieldDescontoCupom)
                    .addGroup(resumoLayout.createSequentialGroup()
                        .addComponent(textFieldTotalPagar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCalcularValor)))
                .addContainerGap())
        );
        resumoLayout.setVerticalGroup(
            resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTaxaPadrao)
                    .addComponent(textFieldTaxaPadrao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDescontosTaxa)
                    .addComponent(textFieldDescontosTaxa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDetalhes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTaxaFinal)
                    .addComponent(textFieldTaxaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCupom)
                    .addComponent(textFieldCupom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAplicarCupom)
                    .addComponent(btnRemoverCupom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDescontoCupom)
                    .addComponent(textFieldDescontoCupom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTotalPagar)
                    .addComponent(textFieldTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalcularValor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        listagem.setBorder(javax.swing.BorderFactory.createTitledBorder("Pedidos cadastrados"));

        tablePedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Número", "Cliente", "Situação", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tablePedidos.setRowHeight(24);
        jScrollPane1.setViewportView(tablePedidos);

        javax.swing.GroupLayout listagemLayout = new javax.swing.GroupLayout(listagem);
        listagem.setLayout(listagemLayout);
        listagemLayout.setHorizontalGroup(
            listagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listagemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        listagemLayout.setVerticalGroup(
            listagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listagemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnNovo.setText("Criar pedido");
        btnSalvarEndereco.setText("Salvar endereço");
        btnFechar.setText("Fechar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(itens, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resumo, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(listagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvarEndereco)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPagar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFechar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(itens, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addComponent(resumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listagem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvarEndereco)
                    .addComponent(btnPagar)
                    .addComponent(btnFechar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnAdicionarProduto;
    private javax.swing.JButton btnAplicarCupom;
    private javax.swing.JButton btnAlterarQuantidade;
    private javax.swing.JButton btnCalcularValor;
    private javax.swing.JButton btnDetalhes;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnRemoverCupom;
    private javax.swing.JButton btnSalvarEndereco;
    private javax.swing.JComboBox<String> boxCliente;
    private javax.swing.JComboBox<String> boxProduto;
    private javax.swing.JPanel dados;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneEndereco;
    private javax.swing.JScrollPane jScrollPaneItens;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelData;
    private javax.swing.JLabel labelEndereco;
    private javax.swing.JLabel labelCupom;
    private javax.swing.JLabel labelDescontoCupom;
    private javax.swing.JLabel labelDescontosTaxa;
    private javax.swing.JLabel labelProduto;
    private javax.swing.JLabel labelQuantidade;
    private javax.swing.JLabel labelNumero;
    private javax.swing.JLabel labelSituacao;
    private javax.swing.JLabel labelTaxaFinal;
    private javax.swing.JLabel labelTaxaPadrao;
    private javax.swing.JLabel labelTotalPagar;
    private javax.swing.JLabel labelValor;
    private javax.swing.JPanel listagem;
    private javax.swing.JPanel itens;
    private javax.swing.JPanel resumo;
    private javax.swing.JTable tableItens;
    private javax.swing.JTable tablePedidos;
    private javax.swing.JTextArea textAreaEndereco;
    private javax.swing.JTextField textFieldCupom;
    private javax.swing.JTextField textFieldDescontoCupom;
    private javax.swing.JTextField textFieldData;
    private javax.swing.JTextField textFieldDescontosTaxa;
    private javax.swing.JTextField textFieldNumero;
    private javax.swing.JTextField textFieldSituacao;
    private javax.swing.JTextField textFieldTaxaFinal;
    private javax.swing.JTextField textFieldTaxaPadrao;
    private javax.swing.JTextField textFieldTotalPagar;
    private javax.swing.JTextField textFieldValor;
    private javax.swing.JTextField textFieldQuantidade;
    // End of variables declaration//GEN-END:variables
}
