package org.vr.software.ui;

import org.vr.software.services.ClienteService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientesFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    private ClienteService clienteService;

    public ClientesFrame(ClienteService clienteService) {
        this.clienteService = clienteService;

        setTitle("Clientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Código", "Nome", "Limite de Compra", "Dia de Fechamento da Fatura"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnAddCliente = new JButton("Adicionar Cliente");
        btnAddCliente.addActionListener(event -> {
            CadastroClienteFrame cadastroClienteFrame = new CadastroClienteFrame(this.clienteService);
            cadastroClienteFrame.setVisible(true);
            cadastroClienteFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    clienteService.carregarClientes(tableModel);
                }
            });
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnAddCliente, BorderLayout.SOUTH);

        add(panel);

        this.clienteService.carregarClientes(tableModel);
    }
}
