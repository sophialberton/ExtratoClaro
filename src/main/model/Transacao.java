//@"
package main.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transacao {
    private LocalDate data;
    private String descricao;
    private BigDecimal valor;
    private Categoria categoria;

    public Transacao(LocalDate data, String descricao, BigDecimal valor) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
    }

    // Getters e Setters
    public LocalDate getData() { return data; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return String.format("%s | %-20s | R$ %8.2f | %s",
            data, descricao, valor,
            (categoria != null) ? categoria.getNome() : "Sem categoria");
    }
}
//"@ | Out-File -FilePath src/main/model/Transacao.java -Encoding UTF8