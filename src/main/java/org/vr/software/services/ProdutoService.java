package org.vr.software.services;

import org.vr.software.entities.Produto;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface ProdutoService {

    void carregarProdutos(DefaultTableModel tableModel);

    List<Produto> carregarProdutosParaCadastroPedido();

    int getProdutoCodigo(String descricao);

    double getProdutoPreco(String descricao);

    void cadastrarProduto(String descricao, float preco);
}
