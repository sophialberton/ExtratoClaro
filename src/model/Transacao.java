/*Representa uma movimentação bancária.
Campos: data, descricao, valor, categoria,
 tipo, etc.*/

package model;
public class Transacao {

    private String data;
    private String descricao;
    private double valor;
    private String categoria;
    private String tipo; // "entrada" ou "saida"

    public Transacao(String data, String descricao, double valor, String categoria, String tipo) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getTipo() {
        return tipo;
    }
}