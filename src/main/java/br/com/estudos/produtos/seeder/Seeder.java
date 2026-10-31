/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.estudos.produtos.seeder;

import br.com.estudos.produtos.model.Categoria;
import br.com.estudos.produtos.servico.ClienteServico;
import br.com.estudos.produtos.servico.CupomServico;
import br.com.estudos.produtos.servico.CalculoPrecoServico;
import br.com.estudos.produtos.servico.CategoriaServico;
import br.com.estudos.produtos.servico.ProdutoServico;
import java.time.LocalDate;
import java.time.LocalDateTime;
import br.com.estudos.produtos.servico.UsuarioServico;

public class Seeder {
    private final CategoriaServico categorias;
    private final ProdutoServico produtos;
    private final CalculoPrecoServico calculo;
    private final UsuarioServico usuarios;
    private final ClienteServico clientes;
    private final CupomServico cupons;

    public Seeder(CategoriaServico categorias, ProdutoServico produtos, CalculoPrecoServico calculo,
            UsuarioServico usuarios, ClienteServico clientes, CupomServico cupons) {
        this.categorias = categorias;
        this.produtos = produtos;
        this.calculo = calculo;
        this.usuarios = usuarios;
        this.clientes = clientes;
        this.cupons = cupons;
    }

    public void executar() {
        usuarios.criarAdministradorInicial();
        clientes.salvar(null, "Ana Silva", "Rua das Flores, 10", "Centro", "Vitória", "ES");
        clientes.salvar(null, "João Souza", "Avenida Brasil, 200", "Praia do Canto", "Vitória", "ES");
        criarCupons();
        Categoria educacao = categorias.salvar(null, "Educação", 25.0);
        Categoria papelaria = categorias.salvar(null, "Papelaria", 30.0);
        Categoria alimentacao = categorias.salvar(null, "Alimentação", 22.0);
        Categoria lazer = categorias.salvar(null, "Lazer", 35.0);
        Categoria entretenimento = categorias.salvar(null, "Entretenimento", 40.0);
        Categoria higiene = categorias.salvar(null, "Higiene", 28.0);
        Categoria limpeza = categorias.salvar(null, "Limpeza", 25.0);
        produto("Livro didático", 45.00, educacao);
        produto("Livro paradidático", 30.00, educacao);
        produto("Mochila escolar", 70.00, educacao);
        produto("Caderno universitário", 16.00, papelaria);
        produto("Lápis grafite HB", 1.20, papelaria);
        produto("Caneta esferográfica azul", 2.20, papelaria);
        produto("Borracha branca", 1.00, papelaria);
        produto("Apontador com depósito", 3.50, papelaria);
        produto("Jogo de tabuleiro", 55.00, lazer);
        produto("Bola recreativa", 40.00, lazer);
        produto("Quebra-cabeça 500 peças", 35.00, lazer);
        produto("Fone de ouvido", 48.00, entretenimento);
        produto("Caixa de som portátil", 80.00, entretenimento);
        produto("Revista de passatempos", 12.00, entretenimento);
        produto("Biscoito integral", 5.50, alimentacao);
        produto("Suco de uva 1 L", 9.00, alimentacao);
        produto("Barra de cereal", 3.20, alimentacao);
        produto("Sabonete", 2.80, higiene);
        produto("Creme dental", 5.50, higiene);
        produto("Detergente líquido", 2.60, limpeza);
        produto("Esponja multiuso", 1.70, limpeza);
        calculo.calcular(LocalDate.now().minusDays(10));
    }

    private void produto(String nome, Double custo, Categoria categoria) {
        produtos.salvar(null, nome, custo, categoria.getId());
    }

    private void criarCupons() {
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 11, 30, 23, 59);

        cupons.salvar("DESC10", 10.0, inicio, fim);
        cupons.salvar("DESC20", 20.0, inicio, fim);
        cupons.salvar("DESC30", 30.0, inicio, fim);
        cupons.salvar("DIAPAI12", 12.0, inicio, fim);
        cupons.salvar("DIAMAE12", 12.0, inicio, fim);
        cupons.salvar("NATAL10", 10.0, inicio, fim);
        cupons.salvar("FESTA15", 15.0, inicio, fim);
        cupons.salvar("BLACK50", 50.0, inicio, fim);
    }
}

