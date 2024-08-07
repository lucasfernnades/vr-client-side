package org.vr.software.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vr.software.entities.Cliente;
import org.vr.software.util.HttpUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClienteServiceImpl implements ClienteService {
    @Override
    public void carregarClientes(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        try {
            String response = HttpUtil.get("http://localhost:8080/clientes");
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cliente = jsonArray.getJSONObject(i);
                Object[] row = {
                        cliente.getInt("id"),
                        cliente.getString("nome"),
                        cliente.getFloat("limiteCompra"),
                        cliente.getInt("fechamentoFatura")
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados dos clientes.");
        }
    }

    @Override
    public List<Cliente> carregarClientesParaCadastroPedido() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            String response = HttpUtil.get("http://localhost:8080/clientes");
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject cliente = jsonArray.getJSONObject(i);
                clientes.add(
                        new Cliente(cliente.getInt("id"),
                                cliente.getString("nome"),
                                cliente.getFloat("limiteCompra"),
                                cliente.getInt("fechamentoFatura")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados dos clientes.");
        }
        return clientes;
    }

    @Override
    public void cadastrarCliente(String nome, float limiteCompra, int fechamentoFatura) {
        try {
            JSONObject jsonCliente = new JSONObject();
            jsonCliente.put("nome", nome);
            jsonCliente.put("limiteCompra", limiteCompra);
            jsonCliente.put("fechamentoFatura", fechamentoFatura);

            String response = HttpUtil.post("http://localhost:8080/clientes", jsonCliente.toString());
            JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao adicionar cliente.");
        }
    }
}
