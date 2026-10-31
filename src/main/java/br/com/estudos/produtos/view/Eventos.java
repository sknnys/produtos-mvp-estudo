/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class Eventos {
    static void adicionarAcao(AbstractButton botao, final Runnable acao) {
        botao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                acao.run();
            }
        });
    }

    static void adicionarSelecao(JTable tabela, final Runnable acao) {
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evento) {
                if (!evento.getValueIsAdjusting()) {
                    acao.run();
                }
            }
        });
    }

    static void adicionarSelecao(JComboBox caixa, final Runnable acao) {
        caixa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                acao.run();
            }
        });
    }
}
