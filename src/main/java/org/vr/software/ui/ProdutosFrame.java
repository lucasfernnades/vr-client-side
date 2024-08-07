package org.vr.software.ui;

import org.vr.software.services.ProdutoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProdutosFrame extends JFrame {

    private JTable table;

    private DefaultTableModel tableModel;

    private ProdutoService produtoService;

    public ProdutosFrame(ProdutoService produtoService) {
        this.produtoService = produtoService;
        setTitle("Produtos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Código", "Descrição", "Preço"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnAddProduto = new JButton("Adicionar Produto");
        btnAddProduto.addActionListener(event -> {
            CadastroProdutoFrame cadastroProdutoFrame = new CadastroProdutoFrame(this.produtoService);
            cadastroProdutoFrame.setVisible(true);
            cadastroProdutoFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    produtoService.carregarProdutos(tableModel);
                }
            });
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnAddProduto, BorderLayout.SOUTH);

        add(panel);

        this.produtoService.carregarProdutos(tableModel);
    }
}
