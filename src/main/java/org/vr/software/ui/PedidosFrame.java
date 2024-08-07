package org.vr.software.ui;

import org.vr.software.services.ClienteService;
import org.vr.software.services.PedidoService;
import org.vr.software.services.ProdutoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PedidosFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private PedidoService pedidoService;
    private ClienteService clienteService;
    private ProdutoService produtoService;

    private JTextField clienteNomeFiltroField;
    private JTextField produtoNomeFiltroField;

    public PedidosFrame(PedidoService pedidoService, ClienteService clienteService, ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.produtoService = produtoService;

        setTitle("Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Código", "Cliente", "Produtos"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnAddPedido = new JButton("Adicionar Pedido");
        btnAddPedido.addActionListener(event -> {
            CadastroPedidoFrame cadastroPedidoFrame = new CadastroPedidoFrame(this.pedidoService,
                    this.clienteService, this.produtoService);
            cadastroPedidoFrame.setVisible(true);
            cadastroPedidoFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    pedidoService.carregarPedidosComFiltro(tableModel, null, null);
                }
            });
        });

        JPanel filtroPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        filtroPanel.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        clienteNomeFiltroField = new JTextField();
        filtroPanel.add(clienteNomeFiltroField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        filtroPanel.add(new JLabel("Produto:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        produtoNomeFiltroField = new JTextField();
        filtroPanel.add(produtoNomeFiltroField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(event -> {
            String clienteNomeFiltro = clienteNomeFiltroField.getText();
            String produtoNomeFiltro = produtoNomeFiltroField.getText();
            pedidoService.carregarPedidosComFiltro(tableModel, clienteNomeFiltro, produtoNomeFiltro);
        });
        filtroPanel.add(btnFiltrar, gbc);

        panel.add(filtroPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnAddPedido, BorderLayout.SOUTH);

        add(panel);

        pedidoService.carregarPedidosComFiltro(tableModel, null, null);
    }
}