package org.vr.software.services;

import org.vr.software.entities.Cliente;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface ClienteService {

    void carregarClientes(DefaultTableModel tableModel);

    List<Cliente> carregarClientesParaCadastroPedido();

    void cadastrarCliente(String nome, float limiteCompra, int fechamentoFatura);
}
