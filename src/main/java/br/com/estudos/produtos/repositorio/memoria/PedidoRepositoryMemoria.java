/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.repositorio.memoria;

import br.projeto.painelpedidos.model.PedidoAbstract;
import br.projeto.painelpedidos.repositorio.IPedidoRepository;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepositoryMemoria implements IPedidoRepository {
    private List<PedidoAbstract> dados = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void salvar(PedidoAbstract pedido) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getIdentificador().equals(pedido.getIdentificador())) {
                dados.set(i, pedido);
                return;
            }
        }
        dados.add(pedido);
    }

    @Override
    public List<PedidoAbstract> buscarTodos() {
        return new ArrayList<>(dados);
    }

    @Override
    public String gerarProximoIdentificador() {
        return "PED-" + proximoId++;
    }
}
