package org.vr.software.services;

import org.json.JSONObject;
import org.vr.software.entities.Cliente;
import org.vr.software.entities.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.List;

public interface PedidoService {

    List<JSONObject> carregarPedidos();

    void registrarPedido(Cliente cliente, List<Produto> produtos, List<Integer> quantidadeProdutos);

    void carregarPedidosComFiltro(DefaultTableModel tableModel, String clienteNomeFiltro,
                             String produtoNomeFiltro);

    void adicionaProdutoNaListagemDoPedido(DefaultTableModel tableModel,
                                           JComboBox<String> produtoComboBox,
                                           JTextField quantidadeField,
                                           List<Integer> quantidadeProdutos,
                                           ProdutoService produtoService);

    void registraPedido(JComboBox<String> clienteComboBox, List<Cliente> clientesCadastrados,
                   DefaultTableModel tableModel, List<Produto> produtosCadastrados,
                   PedidoService pedidoService,
                   List<Integer> quantidadeProdutos);

    void removeProdutoDoPedido(int rowIndex, DefaultTableModel tableModel);

    void listarClientesCadastrados(List<Cliente> clientesCadastrados, ClienteService clienteService,
                                   JComboBox<String> clienteComboBox);

    void listarProdutosCadastrados(List<Produto> produtosCadastrados, ProdutoService produtoService,
                                   JComboBox<String> produtoComboBox);
}
