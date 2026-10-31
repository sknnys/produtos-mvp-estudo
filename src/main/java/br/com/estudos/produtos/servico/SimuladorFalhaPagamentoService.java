/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.servico;

public class SimuladorFalhaPagamentoService {
    private static SimuladorFalhaPagamentoService instancia;

    private SimuladorFalhaPagamentoService() {
    }

    public static SimuladorFalhaPagamentoService getInstancia() {
        if (instancia == null) {
            instancia = new SimuladorFalhaPagamentoService();
        }

        return instancia;
    }

    public void simularFalha() {
        int percentual = (int) (Math.random() * 100);

        if (percentual < 10) {
            throw new RuntimeException("Servidor indisponível");
        }

        if (percentual < 20) {
            throw new RuntimeException("Falha ao processar pagamento");
        }

        if (percentual < 25) {
            throw new RuntimeException("Tempo limite excedido");
        }

        if (percentual < 30) {
            throw new RuntimeException("Falha de comunicação com o serviço de pagamento");
        }
    }
}
