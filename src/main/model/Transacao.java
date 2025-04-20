//@"
package main.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        String cor = valor.doubleValue() < 0 ? "\u001B[31m" : "\u001B[32m";
        return String.format("%s%s | %-50s | R$ %10.2f | %-15s\u001B[0m",
                cor,
                data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                descricao.length() > 50 ? descricao.substring(0, 47) + "..." : descricao,
                valor,
                (categoria != null) ? categoria.getNome() : "Sem categoria");
    }
}
// "@ | Out-File -FilePath src/main/model/Transacao.java -Encoding UTF8