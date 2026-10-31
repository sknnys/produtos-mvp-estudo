package br.com.estudos.produtos.repositorio.memoria;

import br.com.estudos.produtos.model.Cupom;
import br.com.estudos.produtos.repositorio.ICupomRepository;
import java.util.ArrayList;
import java.util.List;

public class CupomRepositoryMemoria implements ICupomRepository {
    private List<Cupom> dados = new ArrayList<>();

    @Override
    public void salvar(Cupom cupom) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getCodigo().equals(cupom.getCodigo())) {
                dados.set(i, cupom);
                return;
            }
        }

        dados.add(cupom);
    }

    @Override
    public Cupom buscarPorCodigo(String codigo) {
        for (Cupom cupom : dados) {
            if (cupom.getCodigo().equals(codigo)) {
                return cupom;
            }
        }

        return null;
    }

    @Override
    public List<Cupom> listarTodos() {
        return new ArrayList<>(dados);
    }

    @Override
    public void remover(String codigo) {
        for (int i = 0; i < dados.size(); i++) {
            if (dados.get(i).getCodigo().equals(codigo)) {
                dados.remove(i);
                return;
            }
        }
    }
}
