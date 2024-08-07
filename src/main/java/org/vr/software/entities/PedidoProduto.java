package org.vr.software.entities;

public class PedidoProduto {

    private Produto produto;

    private int quantidade;

    public PedidoProduto() {
    }

    public PedidoProduto(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "PedidoProduto{" +
                "produto=" + produto +
                ", quantidade=" + quantidade +
                '}';
    }
}
