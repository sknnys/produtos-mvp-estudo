package br.com.estudos.produtos.view;

import br.com.estudos.produtos.servico.PagamentoServico;
import br.com.estudos.produtos.view.contrato.IPagamentoView;

public class PagamentoDialog extends javax.swing.JDialog implements IPagamentoView {

    public PagamentoDialog() {
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
        javax.swing.JOptionPane.showMessageDialog(this, texto, "Atenção",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public boolean exibirConfirmacao(String texto) {
        return javax.swing.JOptionPane.showConfirmDialog(this, texto, "Confirmação",
                javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION;
    }

    @Override
    public String getFormaPagamento() {
        Object forma = boxFormaPagamento.getSelectedItem();
        return forma == null ? "" : forma.toString();
    }

    @Override
    public void mostrarResumo(String numero, String cliente, String dataHora,
            String valorItens, String taxaFinal, String descontoCupom, String totalPagar) {
        textFieldNumero.setText(numero);
        textFieldCliente.setText(cliente);
        textFieldDataHora.setText(dataHora);
        textFieldValorItens.setText(valorItens);
        textFieldTaxaFinal.setText(taxaFinal);
        textFieldDescontoCupom.setText(descontoCupom);
        textFieldTotalPagar.setText(totalPagar);
    }

    @Override
    public void mostrarItens(String[][] linhas) {
        Tabelas.preencher(tableItens, linhas);
    }

    @Override
    public void mostrarCamposForma(String formaPagamento) {
        boolean pixCpf = PagamentoServico.PIX_CPF.equals(formaPagamento);
        boolean pixEmail = PagamentoServico.PIX_EMAIL.equals(formaPagamento);
        boolean cartao = PagamentoServico.CARTAO_CREDITO.equals(formaPagamento);

        labelPix.setText(pixCpf ? "Chave Pix CPF:" : "Chave Pix e-mail:");
        textFieldPix.setEnabled(pixCpf || pixEmail);
        labelNumeroCartao.setEnabled(cartao);
        textFieldNumeroCartao.setEnabled(cartao);
        labelValidade.setEnabled(cartao);
        textFieldValidade.setEnabled(cartao);
        labelCodigoSeguranca.setEnabled(cartao);
        textFieldCodigoSeguranca.setEnabled(cartao);
    }

    @Override
    public void mostrarConfirmacao(String texto) {
        textAreaConfirmacao.setText(texto);
    }

    @Override
    public void definirFinalizado(boolean finalizado) {
        boxFormaPagamento.setEnabled(!finalizado);
        textFieldPix.setEnabled(!finalizado && !PagamentoServico.CARTAO_CREDITO.equals(getFormaPagamento()));
        textFieldNumeroCartao.setEnabled(!finalizado && PagamentoServico.CARTAO_CREDITO.equals(getFormaPagamento()));
        textFieldValidade.setEnabled(!finalizado && PagamentoServico.CARTAO_CREDITO.equals(getFormaPagamento()));
        textFieldCodigoSeguranca.setEnabled(!finalizado && PagamentoServico.CARTAO_CREDITO.equals(getFormaPagamento()));
        btnFinalizar.setEnabled(!finalizado);
        btnVisualizarPedido.setEnabled(finalizado);
    }

    @Override
    public void aoAlterarForma(Runnable acao) {
        Eventos.adicionarSelecao(boxFormaPagamento, acao);
    }

    @Override
    public void aoFinalizar(Runnable acao) {
        Eventos.adicionarAcao(btnFinalizar, acao);
    }

    @Override
    public void aoVisualizarPedido(Runnable acao) {
        Eventos.adicionarAcao(btnVisualizarPedido, acao);
    }

    @Override
    public void aoFechar(Runnable acao) {
        Eventos.adicionarAcao(btnFechar, acao);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        resumo = new javax.swing.JPanel();
        labelNumero = new javax.swing.JLabel();
        textFieldNumero = new javax.swing.JTextField();
        labelCliente = new javax.swing.JLabel();
        textFieldCliente = new javax.swing.JTextField();
        labelDataHora = new javax.swing.JLabel();
        textFieldDataHora = new javax.swing.JTextField();
        labelValorItens = new javax.swing.JLabel();
        textFieldValorItens = new javax.swing.JTextField();
        labelTaxaFinal = new javax.swing.JLabel();
        textFieldTaxaFinal = new javax.swing.JTextField();
        labelDescontoCupom = new javax.swing.JLabel();
        textFieldDescontoCupom = new javax.swing.JTextField();
        labelTotalPagar = new javax.swing.JLabel();
        textFieldTotalPagar = new javax.swing.JTextField();
        itens = new javax.swing.JPanel();
        jScrollPaneItens = new javax.swing.JScrollPane();
        tableItens = new javax.swing.JTable();
        forma = new javax.swing.JPanel();
        labelFormaPagamento = new javax.swing.JLabel();
        boxFormaPagamento = new javax.swing.JComboBox<>();
        labelPix = new javax.swing.JLabel();
        textFieldPix = new javax.swing.JTextField();
        labelNumeroCartao = new javax.swing.JLabel();
        textFieldNumeroCartao = new javax.swing.JTextField();
        labelValidade = new javax.swing.JLabel();
        textFieldValidade = new javax.swing.JTextField();
        labelCodigoSeguranca = new javax.swing.JLabel();
        textFieldCodigoSeguranca = new javax.swing.JTextField();
        confirmacao = new javax.swing.JPanel();
        jScrollPaneConfirmacao = new javax.swing.JScrollPane();
        textAreaConfirmacao = new javax.swing.JTextArea();
        btnFinalizar = new javax.swing.JButton();
        btnVisualizarPedido = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pagamento do pedido");
        setModal(true);
        setPreferredSize(new java.awt.Dimension(1000, 720));

        resumo.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumo do pedido"));
        labelNumero.setText("Número:");
        labelCliente.setText("Cliente:");
        labelDataHora.setText("Data e hora:");
        labelValorItens.setText("Valor dos itens:");
        labelTaxaFinal.setText("Taxa final:");
        labelDescontoCupom.setText("Desconto do cupom:");
        labelTotalPagar.setText("Total a pagar:");
        textFieldNumero.setEditable(false);
        textFieldCliente.setEditable(false);
        textFieldDataHora.setEditable(false);
        textFieldValorItens.setEditable(false);
        textFieldTaxaFinal.setEditable(false);
        textFieldDescontoCupom.setEditable(false);
        textFieldTotalPagar.setEditable(false);

        javax.swing.GroupLayout resumoLayout = new javax.swing.GroupLayout(resumo);
        resumo.setLayout(resumoLayout);
        resumoLayout.setHorizontalGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resumoLayout.createSequentialGroup().addContainerGap()
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNumero).addComponent(labelCliente).addComponent(labelDataHora)
                    .addComponent(labelValorItens).addComponent(labelTaxaFinal)
                    .addComponent(labelDescontoCupom).addComponent(labelTotalPagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldNumero).addComponent(textFieldCliente).addComponent(textFieldDataHora)
                    .addComponent(textFieldValorItens).addComponent(textFieldTaxaFinal)
                    .addComponent(textFieldDescontoCupom).addComponent(textFieldTotalPagar)).addContainerGap()));
        resumoLayout.setVerticalGroup(resumoLayout.createSequentialGroup().addContainerGap()
            .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelNumero).addComponent(textFieldNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelCliente).addComponent(textFieldCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelDataHora).addComponent(textFieldDataHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelValorItens).addComponent(textFieldValorItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelTaxaFinal).addComponent(textFieldTaxaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelDescontoCupom).addComponent(textFieldDescontoCupom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(resumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelTotalPagar).addComponent(textFieldTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap());

        itens.setBorder(javax.swing.BorderFactory.createTitledBorder("Itens"));
        tableItens.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {}, new String [] {"Produto", "Preço unitário", "Quantidade", "Subtotal"}));
        tableItens.setRowHeight(24);
        jScrollPaneItens.setViewportView(tableItens);
        javax.swing.GroupLayout itensLayout = new javax.swing.GroupLayout(itens);
        itens.setLayout(itensLayout);
        itensLayout.setHorizontalGroup(itensLayout.createSequentialGroup().addContainerGap().addComponent(jScrollPaneItens).addContainerGap());
        itensLayout.setVerticalGroup(itensLayout.createSequentialGroup().addContainerGap().addComponent(jScrollPaneItens, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap());

        forma.setBorder(javax.swing.BorderFactory.createTitledBorder("Forma de pagamento"));
        labelFormaPagamento.setText("Forma:");
        boxFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {PagamentoServico.PIX_CPF, PagamentoServico.PIX_EMAIL, PagamentoServico.CARTAO_CREDITO}));
        labelPix.setText("Chave Pix CPF:");
        labelNumeroCartao.setText("Número do cartão:");
        labelValidade.setText("Validade:");
        labelCodigoSeguranca.setText("Código de segurança:");
        javax.swing.GroupLayout formaLayout = new javax.swing.GroupLayout(forma);
        forma.setLayout(formaLayout);
        formaLayout.setHorizontalGroup(formaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formaLayout.createSequentialGroup().addContainerGap().addGroup(formaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(labelFormaPagamento).addComponent(labelPix).addComponent(labelNumeroCartao).addComponent(labelValidade).addComponent(labelCodigoSeguranca)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(formaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(boxFormaPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(textFieldPix).addComponent(textFieldNumeroCartao).addComponent(textFieldValidade).addComponent(textFieldCodigoSeguranca)).addContainerGap()));
        formaLayout.setVerticalGroup(formaLayout.createSequentialGroup().addContainerGap()
            .addGroup(formaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelFormaPagamento).addComponent(boxFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(formaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelPix).addComponent(textFieldPix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(formaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelNumeroCartao).addComponent(textFieldNumeroCartao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(formaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelValidade).addComponent(textFieldValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(formaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelCodigoSeguranca).addComponent(textFieldCodigoSeguranca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap());

        confirmacao.setBorder(javax.swing.BorderFactory.createTitledBorder("Confirmação"));
        textAreaConfirmacao.setColumns(20);
        textAreaConfirmacao.setRows(4);
        textAreaConfirmacao.setEditable(false);
        jScrollPaneConfirmacao.setViewportView(textAreaConfirmacao);
        javax.swing.GroupLayout confirmacaoLayout = new javax.swing.GroupLayout(confirmacao);
        confirmacao.setLayout(confirmacaoLayout);
        confirmacaoLayout.setHorizontalGroup(confirmacaoLayout.createSequentialGroup().addContainerGap().addComponent(jScrollPaneConfirmacao).addContainerGap());
        confirmacaoLayout.setVerticalGroup(confirmacaoLayout.createSequentialGroup().addContainerGap().addComponent(jScrollPaneConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap());

        btnFinalizar.setText("Finalizar pagamento");
        btnVisualizarPedido.setText("Visualizar pedido");
        btnVisualizarPedido.setEnabled(false);
        btnFechar.setText("Fechar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addComponent(resumo, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(itens, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)).addComponent(forma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(confirmacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(btnFinalizar).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnVisualizarPedido).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnFechar))).addContainerGap()));
        layout.setVerticalGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(resumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(itens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(forma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(confirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnFinalizar).addComponent(btnVisualizarPedido).addComponent(btnFechar)).addContainerGap());

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnVisualizarPedido;
    private javax.swing.JComboBox<String> boxFormaPagamento;
    private javax.swing.JPanel confirmacao;
    private javax.swing.JPanel forma;
    private javax.swing.JPanel itens;
    private javax.swing.JScrollPane jScrollPaneConfirmacao;
    private javax.swing.JScrollPane jScrollPaneItens;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelCodigoSeguranca;
    private javax.swing.JLabel labelDataHora;
    private javax.swing.JLabel labelDescontoCupom;
    private javax.swing.JLabel labelFormaPagamento;
    private javax.swing.JLabel labelNumero;
    private javax.swing.JLabel labelNumeroCartao;
    private javax.swing.JLabel labelPix;
    private javax.swing.JLabel labelTaxaFinal;
    private javax.swing.JLabel labelTotalPagar;
    private javax.swing.JLabel labelValidade;
    private javax.swing.JLabel labelValorItens;
    private javax.swing.JPanel resumo;
    private javax.swing.JTable tableItens;
    private javax.swing.JTextArea textAreaConfirmacao;
    private javax.swing.JTextField textFieldCliente;
    private javax.swing.JTextField textFieldCodigoSeguranca;
    private javax.swing.JTextField textFieldDataHora;
    private javax.swing.JTextField textFieldDescontoCupom;
    private javax.swing.JTextField textFieldNumero;
    private javax.swing.JTextField textFieldNumeroCartao;
    private javax.swing.JTextField textFieldPix;
    private javax.swing.JTextField textFieldTaxaFinal;
    private javax.swing.JTextField textFieldTotalPagar;
    private javax.swing.JTextField textFieldValidade;
    private javax.swing.JTextField textFieldValorItens;
    // End of variables declaration//GEN-END:variables
}
