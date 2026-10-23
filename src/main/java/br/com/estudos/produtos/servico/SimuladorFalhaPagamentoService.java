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

    public String simularFalha() {
        int percentual = (int) (Math.random() * 100);

        if (percentual < 10) {
            return "Servidor indisponível";
        }

        if (percentual < 20) {
            return "Falha ao processar pagamento";
        }

        if (percentual < 25) {
            return "Tempo limite excedido";
        }

        if (percentual < 30) {
            return "Falha de comunicação com o serviço de pagamento";
        }

        return null;
    }
}
