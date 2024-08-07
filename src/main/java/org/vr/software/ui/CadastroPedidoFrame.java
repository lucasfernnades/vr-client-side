package org.vr.software.ui;

import org.vr.software.entities.Cliente;
import org.vr.software.entities.Produto;
import org.vr.software.services.ClienteService;
import org.vr.software.services.PedidoService;
import org.vr.software.services.ProdutoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CadastroPedidoFrame extends JFrame {
    private JComboBox<String> clienteComboBox;

    private JComboBox<String> produtoComboBox;

    private JTextField quantidadeField;

    private DefaultTableModel tableModel;

    private PedidoService pedidoService;

    private ClienteService clienteService;

    private ProdutoService produtoService;

    private List<Cliente> clientesCadastrados;

    private List<Produto> produtosCadastrados;

    private List<Integer> quantidadeProdutos;

    public CadastroPedidoFrame(PedidoService pedidoService, ClienteService clienteService,
                               ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.produtoService = produtoService;

        quantidadeProdutos = new ArrayList<>();

        setTitle("Cadastro de Pedido");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Cliente:"));
        clienteComboBox = new JComboBox<>();
        pedidoService.listarClientesCadastrados(clientesCadastrados, clienteService, clienteComboBox);
        inputPanel.add(clienteComboBox);

        inputPanel.add(new JLabel("Produto:"));
        produtoComboBox = new JComboBox<>();
        pedidoService.listarProdutosCadastrados(produtosCadastrados, produtoService, produtoComboBox);
        inputPanel.add(produtoComboBox);

        inputPanel.add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField();
        quantidadeField.setText("1");
        inputPanel.add(quantidadeField);

        panel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Código", "Descrição", "Preço", "Quantidade"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAddProduto = new JButton("Adicionar Produto");
        btnAddProduto.addActionListener(event -> pedidoService.adicionaProdutoNaListagemDoPedido(
                tableModel,
                produtoComboBox,
                quantidadeField,
                quantidadeProdutos,
                produtoService
        ));

        JButton btnRemoveProduto = new JButton("Remover Produto");
        btnRemoveProduto.addActionListener(event ->
                pedidoService.removeProdutoDoPedido(table.getSelectedRow(), tableModel));

        JButton btnSave = new JButton("Salvar Pedido");
        btnSave.addActionListener(event -> pedidoService.registraPedido(
                clienteComboBox, clientesCadastrados, tableModel,
                produtosCadastrados, pedidoService, quantidadeProdutos
        ));

        dispose();

        buttonPanel.add(btnAddProduto);
        buttonPanel.add(btnRemoveProduto);
        buttonPanel.add(btnSave);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }
}
