/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.view;

import br.com.estudos.produtos.model.Usuario;
import br.com.estudos.produtos.view.contrato.IUsuariosView;

public class UsuariosDialog extends javax.swing.JDialog implements IUsuariosView {

    /**
     * Creates new form UsuariosDialog
     */
    public UsuariosDialog() {
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
    public String getNome() {
        return textFieldNome.getText();
    }

    @Override
    public String getEmail() {
        return textFieldEmail.getText();
    }

    @Override
    public String getNomeUsuario() {
        return textFieldNomeUsuario.getText();
    }

    @Override
    public String getSenha() {
        return new String(textFieldSenha.getPassword());
    }

    @Override
    public String getConfirmaSenha() {
        return new String(textFieldConfirmaSenha.getPassword());
    }

    @Override
    public String getPerfil() {
        Object perfil = boxPerfil.getSelectedItem();
        return perfil == null ? "" : perfil.toString();
    }

    @Override
    public String getCliente() {
        Object cliente = boxCliente.getSelectedItem();
        return cliente == null ? "" : cliente.toString();
    }

    @Override
    public int getLinha() {
        return tableUsuarios.getSelectedRow();
    }

    @Override
    public void mostrarDados(String nome, String email, String nomeUsuario,
            String perfil, String status, String cliente) {
        textFieldNome.setText(nome);
        textFieldEmail.setText(email);
        textFieldNomeUsuario.setText(nomeUsuario);
        textFieldSenha.setText("");
        textFieldConfirmaSenha.setText("");
        boxPerfil.setSelectedItem(perfil);
        textFieldStatus.setText(status);
        boxCliente.setSelectedItem(cliente);
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
    public void mostrarUsuarios(String[][] linhas) {
        Tabelas.preencher(tableUsuarios, linhas);
    }

    @Override
    public void selecionarLinha(int indice) {
        if (indice < 0) {
            tableUsuarios.clearSelection();
        } else {
            tableUsuarios.setRowSelectionInterval(indice, indice);
        }
    }

    @Override
    public void definirModo(boolean editando, boolean selecionado, boolean administrador,
            boolean habilitado, String descricao) {
        textFieldNome.setEditable(editando);
        textFieldEmail.setEditable(editando);
        textFieldNomeUsuario.setEditable(editando);
        textFieldSenha.setEditable(editando);
        textFieldConfirmaSenha.setEditable(editando);
        boxPerfil.setEnabled(editando);
        tableUsuarios.setEnabled(!editando);

        btnNovo.setEnabled(!editando);
        btnFechar.setEnabled(!editando);
        btnEditar.setEnabled(!editando && selecionado && !administrador);
        btnExcluir.setEnabled(!editando && selecionado && !administrador);
        btnHabilitar.setEnabled(!editando && selecionado && !administrador && !habilitado);
        btnDesabilitar.setEnabled(!editando && selecionado && !administrador && habilitado);
        btnSalvar.setEnabled(editando);
        btnCancelar.setEnabled(editando);
        btnMostrarSenha.setEnabled(editando);
        atualizarClientePorPerfil();

        if (editando) {
            textFieldNome.requestFocusInWindow();
        }

        setDefaultCloseOperation(editando ? javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE
                : javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void atualizarClientePorPerfil() {
        boolean perfilCliente = "CLIENTE".equals(getPerfil());
        boxCliente.setEnabled(boxPerfil.isEnabled() && perfilCliente);
        btnIncluirCliente.setEnabled(boxPerfil.isEnabled() && perfilCliente);
        if (!perfilCliente) {
            boxCliente.setSelectedItem("");
        }
    }

    @Override
    public void alternarSenha() {
        char caractere = textFieldSenha.getEchoChar();
        char novoCaractere = caractere == 0 ? '•' : (char) 0;
        textFieldSenha.setEchoChar(novoCaractere);
        textFieldConfirmaSenha.setEchoChar(novoCaractere);
        btnMostrarSenha.setText(caractere == 0 ? "Mostrar senha" : "Ocultar senha");
    }

    @Override
    public void aoNovo(Runnable acao) {
        Eventos.adicionarAcao(btnNovo, acao);
    }

    @Override
    public void aoEditar(Runnable acao) {
        Eventos.adicionarAcao(btnEditar, acao);
    }

    @Override
    public void aoExcluir(Runnable acao) {
        Eventos.adicionarAcao(btnExcluir, acao);
    }

    @Override
    public void aoSalvar(Runnable acao) {
        Eventos.adicionarAcao(btnSalvar, acao);
    }

    @Override
    public void aoCancelar(Runnable acao) {
        Eventos.adicionarAcao(btnCancelar, acao);
    }

    @Override
    public void aoHabilitar(Runnable acao) {
        Eventos.adicionarAcao(btnHabilitar, acao);
    }

    @Override
    public void aoDesabilitar(Runnable acao) {
        Eventos.adicionarAcao(btnDesabilitar, acao);
    }

    @Override
    public void aoMostrarSenha(Runnable acao) {
        Eventos.adicionarAcao(btnMostrarSenha, acao);
    }

    @Override
    public void aoIncluirCliente(Runnable acao) {
        Eventos.adicionarAcao(btnIncluirCliente, acao);
    }

    @Override
    public void aoSelecionar(Runnable acao) {
        Eventos.adicionarSelecao(tableUsuarios, acao);
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
        labelNome = new javax.swing.JLabel();
        textFieldNome = new javax.swing.JTextField();
        labelEmail = new javax.swing.JLabel();
        textFieldEmail = new javax.swing.JTextField();
        labelNomeUsuario = new javax.swing.JLabel();
        textFieldNomeUsuario = new javax.swing.JTextField();
        labelSenha = new javax.swing.JLabel();
        textFieldSenha = new javax.swing.JPasswordField();
        labelConfirmaSenha = new javax.swing.JLabel();
        textFieldConfirmaSenha = new javax.swing.JPasswordField();
        painelSenhas = new javax.swing.JPanel();
        labelPerfil = new javax.swing.JLabel();
        boxPerfil = new javax.swing.JComboBox<>();
        labelStatus = new javax.swing.JLabel();
        textFieldStatus = new javax.swing.JTextField();
        labelCliente = new javax.swing.JLabel();
        boxCliente = new javax.swing.JComboBox<>();
        botoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnHabilitar = new javax.swing.JButton();
        btnDesabilitar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnMostrarSenha = new javax.swing.JButton();
        btnIncluirCliente = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        listagem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUsuarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Usuários");
        setModal(true);
        setPreferredSize(new java.awt.Dimension(860, 580));

        dados.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do usuário"));

        labelNome.setText("Nome completo:");
        labelEmail.setText("E-mail:");
        labelNomeUsuario.setText("Nome de usuário:");
        labelSenha.setText("Senha:");
        labelConfirmaSenha.setText("Confirmar senha:");
        labelPerfil.setText("Perfil:");
        labelStatus.setText("Status:");
        labelCliente.setText("Cliente associado:");

        textFieldNome.setEditable(false);
        textFieldNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNomeActionPerformed(evt);
            }
        });
        textFieldEmail.setEditable(false);
        textFieldEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldEmailActionPerformed(evt);
            }
        });
        textFieldNomeUsuario.setEditable(false);
        textFieldNomeUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNomeUsuarioActionPerformed(evt);
            }
        });
        textFieldSenha.setEditable(false);
        textFieldSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldSenhaActionPerformed(evt);
            }
        });
        textFieldConfirmaSenha.setEditable(false);
        textFieldConfirmaSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldConfirmaSenhaActionPerformed(evt);
            }
        });
        boxPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CLIENTE", "ATENDENTE" }));
        boxPerfil.setEnabled(false);
        boxPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxPerfilActionPerformed(evt);
            }
        });
        boxPerfil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                boxPerfilKeyPressed(evt);
            }
        });
        textFieldStatus.setEditable(false);
        boxCliente.setEnabled(false);
        boxCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                boxClienteKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout painelSenhasLayout = new javax.swing.GroupLayout(painelSenhas);
        painelSenhas.setLayout(painelSenhasLayout);
        painelSenhasLayout.setHorizontalGroup(
            painelSenhasLayout.createSequentialGroup()
            .addComponent(labelSenha)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textFieldSenha)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(labelConfirmaSenha)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textFieldConfirmaSenha)
        );
        painelSenhasLayout.setVerticalGroup(
            painelSenhasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(labelSenha)
            .addComponent(textFieldSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(labelConfirmaSenha)
            .addComponent(textFieldConfirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnNovo.setText("Novo");
        btnEditar.setText("Editar");
        btnExcluir.setText("Excluir");
        btnHabilitar.setText("Habilitar");
        btnDesabilitar.setText("Desabilitar");
        btnSalvar.setText("Salvar");
        btnCancelar.setText("Cancelar");
        btnMostrarSenha.setText("Mostrar senha");
        btnIncluirCliente.setText("Incluir cliente");
        btnFechar.setText("Fechar");

        listagem.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuários cadastrados"));

        tableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Nome", "Nome de usuário", "Perfil", "Status", "Cliente associado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableUsuarios.setRowHeight(24);
        jScrollPane1.setViewportView(tableUsuarios);

        javax.swing.GroupLayout dadosLayout = new javax.swing.GroupLayout(dados);
        dados.setLayout(dadosLayout);
        dadosLayout.setHorizontalGroup(
            dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNome)
                    .addComponent(labelEmail)
                    .addComponent(labelNomeUsuario)
                    .addComponent(labelPerfil)
                    .addComponent(labelStatus)
                    .addComponent(labelCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldNome)
                    .addComponent(textFieldEmail)
                    .addComponent(textFieldNomeUsuario)
                    .addComponent(painelSenhas)
                    .addComponent(boxPerfil)
                    .addComponent(textFieldStatus)
                    .addGroup(dadosLayout.createSequentialGroup()
                        .addComponent(boxCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnIncluirCliente)))
                .addContainerGap())
        );
        dadosLayout.setVerticalGroup(
            dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(textFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEmail)
                    .addComponent(textFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomeUsuario)
                    .addComponent(textFieldNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelSenhas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPerfil)
                    .addComponent(boxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelStatus)
                    .addComponent(textFieldStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCliente)
                    .addComponent(boxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIncluirCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout botoesLayout = new javax.swing.GroupLayout(botoes);
        botoes.setLayout(botoesLayout);
        botoesLayout.setHorizontalGroup(
            botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHabilitar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDesabilitar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMostrarSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        botoesLayout.setVerticalGroup(
            botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnEditar)
                    .addComponent(btnExcluir)
                    .addComponent(btnHabilitar)
                    .addComponent(btnDesabilitar)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar)
                    .addComponent(btnMostrarSenha)
                    .addComponent(btnFechar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void boxPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxPerfilActionPerformed
        atualizarClientePorPerfil();
    }//GEN-LAST:event_boxPerfilActionPerformed

    private void textFieldNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldNomeActionPerformed
        textFieldEmail.requestFocusInWindow();
    }//GEN-LAST:event_textFieldNomeActionPerformed

    private void textFieldEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldEmailActionPerformed
        textFieldNomeUsuario.requestFocusInWindow();
    }//GEN-LAST:event_textFieldEmailActionPerformed

    private void textFieldNomeUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldNomeUsuarioActionPerformed
        textFieldSenha.requestFocusInWindow();
    }//GEN-LAST:event_textFieldNomeUsuarioActionPerformed

    private void textFieldSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldSenhaActionPerformed
        textFieldConfirmaSenha.requestFocusInWindow();
    }//GEN-LAST:event_textFieldSenhaActionPerformed

    private void textFieldConfirmaSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldConfirmaSenhaActionPerformed
        boxPerfil.requestFocusInWindow();
    }//GEN-LAST:event_textFieldConfirmaSenhaActionPerformed

    private void boxPerfilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_boxPerfilKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            if (Usuario.CLIENTE.equals(getPerfil())) {
                boxCliente.requestFocusInWindow();
            } else {
                btnSalvar.requestFocusInWindow();
            }
        }
    }//GEN-LAST:event_boxPerfilKeyPressed

    private void boxClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_boxClienteKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            btnSalvar.requestFocusInWindow();
        }
    }//GEN-LAST:event_boxClienteKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxCliente;
    private javax.swing.JComboBox<String> boxPerfil;
    private javax.swing.JPanel botoes;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDesabilitar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnHabilitar;
    private javax.swing.JButton btnIncluirCliente;
    private javax.swing.JButton btnMostrarSenha;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JPanel dados;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelConfirmaSenha;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelNomeUsuario;
    private javax.swing.JLabel labelPerfil;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JPanel listagem;
    private javax.swing.JPanel painelSenhas;
    private javax.swing.JTable tableUsuarios;
    private javax.swing.JPasswordField textFieldConfirmaSenha;
    private javax.swing.JTextField textFieldEmail;
    private javax.swing.JTextField textFieldNome;
    private javax.swing.JTextField textFieldNomeUsuario;
    private javax.swing.JPasswordField textFieldSenha;
    private javax.swing.JTextField textFieldStatus;
    // End of variables declaration//GEN-END:variables
}
