package org.vr.software.ui;

import org.vr.software.services.ProdutoService;

import javax.swing.*;
import java.awt.*;

public class CadastroProdutoFrame extends JFrame {
    private JTextField txtDescricao, txtPreco;

    private ProdutoService produtoService;

    public CadastroProdutoFrame(ProdutoService produtoService) {
        this.produtoService = produtoService;
        setTitle("Cadastro de Produtos");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        panel.add(txtDescricao);

        panel.add(new JLabel("Preço:"));
        txtPreco = new JTextField();
        panel.add(txtPreco);

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(event -> {
            String descricao = txtDescricao.getText();
            float preco = Float.parseFloat(txtPreco.getText());
            produtoService.cadastrarProduto(descricao, preco);
            dispose();
        });

        panel.add(btnSave);

        add(panel);
    }
}
