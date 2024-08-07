package org.vr.software.entities;

import java.util.List;

public class Pedido {

    private int id;

    private Cliente cliente;

    private List<PedidoProduto> produtos;

    private boolean status;



    public Pedido() {
    }

    public Pedido(int id, Cliente cliente, List<PedidoProduto> produtos, boolean status) {
        this.id = id;
        this.cliente = cliente;
        this.produtos = produtos;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PedidoProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<PedidoProduto> produtos) {
        this.produtos = produtos;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", produtos=" + produtos +
                ", status=" + status +
                '}';
    }
}
