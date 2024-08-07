package org.vr.software.ui;

import org.vr.software.services.*;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    private ClienteService clienteService;

    private ProdutoService produtoService;

    private PedidoService pedidoService;

    public MenuFrame() {
        this.clienteService = new ClienteServiceImpl();
        this.produtoService = new ProdutoServiceImpl();
        this.pedidoService = new PedidoServiceImpl();

        setTitle("Sistema de Gestão de Vendas");
        setSize(500, 210);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel("images/background.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton btnClientes = new JButton("Clientes");
        JButton btnProdutos = new JButton("Produtos");
        JButton btnPedidos = new JButton("Pedidos");

        buttonPanel.add(btnClientes);
        buttonPanel.add(btnProdutos);
        buttonPanel.add(btnPedidos);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnClientes.addActionListener(e -> new ClientesFrame(clienteService).setVisible(true));

        btnProdutos.addActionListener(e -> new ProdutosFrame(produtoService).setVisible(true));

        btnPedidos.addActionListener(e -> new PedidosFrame(pedidoService,
                clienteService, produtoService).setVisible(true));

        add(backgroundPanel);
    }
}
