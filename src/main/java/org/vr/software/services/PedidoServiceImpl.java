package org.vr.software.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.vr.software.entities.Cliente;
import org.vr.software.entities.Pedido;
import org.vr.software.entities.PedidoProduto;
import org.vr.software.entities.Produto;
import org.vr.software.util.HttpUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoServiceImpl implements PedidoService {
    @Override
    public List<JSONObject> carregarPedidos() {
        List<JSONObject> pedidos = new ArrayList<>();
        try {
            String response = HttpUtil.get("http://localhost:8080/pedidos");
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject pedido = jsonArray.getJSONObject(i);
                pedidos.add(pedido);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados dos pedidos.");
        }
        return pedidos;
    }

    @Override
    public void registrarPedido(Cliente cliente, List<Produto> produtos, List<Integer> quantidadeProdutos) {
        try {
            Pedido pedidoDTO = new Pedido();

            Cliente clienteDTO = new Cliente();
            clienteDTO.setId(cliente.getId());
            pedidoDTO.setCliente(clienteDTO);

            List<PedidoProduto> pedidoProdutos = new ArrayList<>();
            for (int i = 0; i < produtos.size(); i++) {
                Produto produto = produtos.get(i);
                int quantidade = quantidadeProdutos.get(i);

                Produto produtoDTO = new Produto();
                produtoDTO.setId(produto.getId());

                PedidoProduto pedidoProdutoDTO = new PedidoProduto();
                pedidoProdutoDTO.setProduto(produtoDTO);
                pedidoProdutoDTO.setQuantidade(quantidade);

                pedidoProdutos.add(pedidoProdutoDTO);
            }
            pedidoDTO.setProdutos(pedidoProdutos);
            pedidoDTO.setStatus(true);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPedido = objectMapper.writeValueAsString(pedidoDTO);

            System.out.println(jsonPedido);

            String response = HttpUtil.post("http://localhost:8080/pedidos", jsonPedido);
            JOptionPane.showMessageDialog(null, "Pedido adicionado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar pedido.");
        }
    }

    @Override
    public void carregarPedidosComFiltro(DefaultTableModel tableModel, String clienteNomeFiltro,
                                         String produtoNomeFiltro) {
        tableModel.setRowCount(0);
        List<JSONObject> pedidos = carregarPedidos();

        for (JSONObject pedido : pedidos) {
            int id = pedido.getInt("id");
            JSONObject cliente = pedido.getJSONObject("cliente");
            String clienteNome = cliente.getString("nome");

            if (clienteNomeFiltro != null && !clienteNome.toLowerCase().contains(clienteNomeFiltro.toLowerCase())) {
                continue;
            }

            JSONArray produtosArray = pedido.getJSONArray("produtos");
            StringBuilder produtos = new StringBuilder();
            boolean produtoEncontrado = false;

            for (int i = 0; i < produtosArray.length(); i++) {
                JSONObject produto = produtosArray.getJSONObject(i);
                JSONObject p = produto.getJSONObject("produto");
                String produtoNome = p.getString("descricao");

                if (produtoNomeFiltro != null && produtoNome.toLowerCase().contains(produtoNomeFiltro.toLowerCase())) {
                    produtoEncontrado = true;
                }

                produtos.append("qtd: " + produto.getInt("quantidade") + " ");
                produtos.append(produtoNome);
                if (i < produtosArray.length() - 1) {
                    produtos.append("; ");
                }
            }

            if (produtoNomeFiltro != null && !produtoEncontrado) {
                continue;
            }

            tableModel.addRow(new Object[]{id, clienteNome, produtos.toString()});
        }
    }

    @Override
    public void adicionaProdutoNaListagemDoPedido(DefaultTableModel tableModel,
                                                  JComboBox<String> produtoComboBox,
                                                  JTextField quantidadeField,
                                                  List<Integer> quantidadeProdutos,
                                                  ProdutoService produtoService) {
        String produto = (String) produtoComboBox.getSelectedItem();
        int quantidade = Integer.parseInt(quantidadeField.getText());
        quantidadeProdutos.add(quantidade);

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 1).equals(produto)) {
                tableModel.setValueAt(quantidade, i, 3);
                return;
            }
        }

        tableModel.addRow(new Object[]{
                produtoService.getProdutoCodigo(produto),
                produto,
                produtoService.getProdutoPreco(produto),
                quantidade
        });
    }

    @Override
    public void registraPedido(JComboBox<String> clienteComboBox, List<Cliente> clientesCadastrados,
                               DefaultTableModel tableModel, List<Produto> produtosCadastrados,
                               PedidoService pedidoService,
                               List<Integer> quantidadeProdutos) {
        String cliente = (String) clienteComboBox.getSelectedItem();
        clientesCadastrados.removeIf(c -> !c.getNome().equals(cliente));

        Cliente clienteEscolhido = clientesCadastrados.get(0);

        List<Produto> produtosEscolhidos = new ArrayList<>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int idProduto = (Integer) tableModel.getValueAt(i, 0);
            List<Produto> produtoTemp = produtosCadastrados.stream()
                    .filter(p -> p.getId() == idProduto)
                    .collect(Collectors.toList());
            produtosEscolhidos.add(produtoTemp.get(0));
        }

        pedidoService.registrarPedido(clienteEscolhido, produtosEscolhidos, quantidadeProdutos);
    }

    @Override
    public void removeProdutoDoPedido(int rowIndex, DefaultTableModel tableModel) {
        if (rowIndex >= 0) {
            tableModel.removeRow(rowIndex);
        }
    }

    @Override
    public void listarClientesCadastrados(List<Cliente> clientesCadastrados, ClienteService clienteService,
                                          JComboBox<String> clienteComboBox) {
        clientesCadastrados = clienteService.carregarClientesParaCadastroPedido();

        for (Cliente cliente : clientesCadastrados) {
            clienteComboBox.addItem(cliente.getNome());
        }
    }

    @Override
    public void listarProdutosCadastrados(List<Produto> produtosCadastrados, ProdutoService produtoService,
                                          JComboBox<String> produtoComboBox) {
        produtosCadastrados = produtoService.carregarProdutosParaCadastroPedido();

        for (Produto produto : produtosCadastrados) {
            produtoComboBox.addItem(produto.getDescricao());
        }
    }
}