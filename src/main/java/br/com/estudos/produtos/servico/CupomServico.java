package br.com.estudos.produtos.servico;

import br.com.estudos.produtos.model.Cupom;
import br.com.estudos.produtos.model.Pedido;
import br.com.estudos.produtos.repositorio.ICupomRepository;
import java.time.LocalDateTime;
import java.util.List;

public class CupomServico {
    private ICupomRepository cupomRepository;

    public CupomServico(ICupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    public void salvar(String codigo, Double percentualDesconto,
            LocalDateTime inicioValidade, LocalDateTime fimValidade) {
        validar(codigo, percentualDesconto, inicioValidade, fimValidade);

        Cupom cupom = new Cupom(codigo.trim(), percentualDesconto,
                inicioValidade, fimValidade);
        cupomRepository.salvar(cupom);
    }

    public List<Cupom> listarTodos() {
        return cupomRepository.listarTodos();
    }

    public void removerDisponivel(String codigo) {
        cupomRepository.remover(codigo);
    }

    public void aplicar(Pedido pedido, String codigo) {
        if (pedido == null) {
            throw new RegraNegocioException("Informe um pedido.");
        }

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new RegraNegocioException("Informe o código do cupom.");
        }

        Cupom cupom = cupomRepository.buscarPorCodigo(codigo.trim());

        if (cupom == null) {
            throw new RegraNegocioException("Cupom não encontrado.");
        }

        if (!cupom.estaValido()) {
            throw new RegraNegocioException("Cupom fora da validade.");
        }

        if (pedido.temCupom() && cupom.getPercentualDesconto()
                <= pedido.getPercentualCupom()) {
            throw new RegraNegocioException("O novo cupom precisa ter percentual maior.");
        }

        pedido.aplicarCupom(cupom.getCodigo(), cupom.getPercentualDesconto());
    }

    private void validar(String codigo, Double percentualDesconto,
            LocalDateTime inicioValidade, LocalDateTime fimValidade) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new RegraNegocioException("Informe o código do cupom.");
        }

        if (percentualDesconto == null || percentualDesconto <= 0) {
            throw new RegraNegocioException("Informe um percentual maior que zero.");
        }

        if (inicioValidade == null || fimValidade == null
                || fimValidade.isBefore(inicioValidade)) {
            throw new RegraNegocioException("Informe uma validade correta para o cupom.");
        }
    }
}
