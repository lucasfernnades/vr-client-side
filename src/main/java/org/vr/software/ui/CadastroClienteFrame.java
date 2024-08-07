package org.vr.software.ui;

import org.vr.software.services.ClienteService;

import javax.swing.*;
import java.awt.*;

public class CadastroClienteFrame extends JFrame {
    private JTextField txtNome, txtLimiteCompra, txtFechamentoFatura;

    private ClienteService clienteService;

    public CadastroClienteFrame(ClienteService clienteService) {
        this.clienteService = clienteService;
        setTitle("Cadastro de Clientes");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panel.add(txtNome);

        panel.add(new JLabel("Limite de Compra:"));
        txtLimiteCompra = new JTextField();
        panel.add(txtLimiteCompra);

        panel.add(new JLabel("Dia de Fechamento:"));
        txtFechamentoFatura = new JTextField();
        panel.add(txtFechamentoFatura);

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(event -> {
            String nome = txtNome.getText();
            float limiteCompra = Float.parseFloat(txtLimiteCompra.getText());
            int fechamentoFatura = Integer.parseInt(txtFechamentoFatura.getText());
            clienteService.cadastrarCliente(nome, limiteCompra, fechamentoFatura);
            dispose();
        });

        panel.add(btnSave);

        add(panel);
    }
}
