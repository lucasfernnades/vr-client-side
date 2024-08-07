package org.vr.software.entities;

public class Cliente {
    private int id;
    private String nome;
    private double limiteCompra;
    private int fechamentoFatura;

    public Cliente() {
    }

    public Cliente(int id, String nome, double limiteCompra, int fechamentoFatura) {
        this.id = id;
        this.nome = nome;
        this.limiteCompra = limiteCompra;
        this.fechamentoFatura = fechamentoFatura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLimiteCompra() {
        return limiteCompra;
    }

    public void setLimiteCompra(double limiteCompra) {
        this.limiteCompra = limiteCompra;
    }

    public int getFechamentoFatura() {
        return fechamentoFatura;
    }

    public void setFechamentoFatura(int fechamentoFatura) {
        this.fechamentoFatura = fechamentoFatura;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", limiteCompra=" + limiteCompra +
                ", fechamentoFatura=" + fechamentoFatura +
                '}';
    }
}
