package org.vr.software.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vr.software.entities.Produto;
import org.vr.software.util.HttpUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProdutoServiceImpl implements ProdutoService {

    @Override
    public void carregarProdutos(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        try {
            String response = HttpUtil.get("http://localhost:8080/produtos");
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject produto = jsonArray.getJSONObject(i);
                Object[] row = {
                        produto.getInt("id"),
                        produto.getString("descricao"),
                        produto.getFloat("preco")
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados dos produtos.");
        }
    }

    @Override
    public List<Produto> carregarProdutosParaCadastroPedido() {
        List<Produto> produtos = new ArrayList<>();
        try {
            String response = HttpUtil.get("http://localhost:8080/produtos");
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject produto = jsonArray.getJSONObject(i);
                produtos.add(
                        new Produto(
                                produto.getInt("id"),
                                produto.getString("descricao"),
                                produto.getFloat("preco")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados dos produtos.");
        }
        return produtos;
    }

    @Override
    public int getProdutoCodigo(String descricao) {
        try {
            String response = HttpUtil.get("http://localhost:8080/produtos");
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject produto = jsonArray.getJSONObject(i);
                if (produto.getString("descricao").equals(descricao)) {
                    return produto.getInt("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public double getProdutoPreco(String descricao) {
        try {
            String response = HttpUtil.get("http://localhost:8080/produtos");
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject produto = jsonArray.getJSONObject(i);
                if (produto.getString("descricao").equals(descricao)) {
                    return produto.getDouble("preco");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public void cadastrarProduto(String descricao, float preco) {
        try {
            JSONObject jsonProduto = new JSONObject();
            jsonProduto.put("descricao", descricao);
            jsonProduto.put("preco", preco);

            String response = HttpUtil.post("http://localhost:8080/produtos", jsonProduto.toString());
            JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar produto.");
        }
    }
}
