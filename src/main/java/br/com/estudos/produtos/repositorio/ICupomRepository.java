package br.com.estudos.produtos.repositorio;

import br.com.estudos.produtos.model.Cupom;
import java.util.List;

public interface ICupomRepository {
    void salvar(Cupom cupom);
    Cupom buscarPorCodigo(String codigo);
    List<Cupom> listarTodos();
    void remover(String codigo);
}
