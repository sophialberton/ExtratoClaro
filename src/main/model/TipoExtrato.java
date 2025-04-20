package main.model;

public enum TipoExtrato {
    NUBANK_CONTA("Nubank (Conta)", 
        new String[]{"Data,Valor,Identificador,Descrição"}, 
        ",",
        new int[]{0, 1, 3}, // data, valor, descrição
        "dd/MM/yyyy"),

    NUBANK_CARTAO("Nubank (Cartão)", 
        new String[]{"date,title,amount"}, 
        ",",
        new int[]{0, 2, 1}, // data, valor, descrição
        "yyyy-MM-dd"),

    MERCADO_PAGO("Mercado Pago", 
        new String[]{"\"Data de pagamento\",\"Tipo de operação\",\"Número do movimento\",\"Operação relacionada\",\"Valor\""}, 
        "\",\"",
        new int[]{0, 4, 1}, // data, valor, descrição
        "yyyy-MM-dd'T'HH:mm:ss'Z'");

    public final String nome;
    public final String[] cabecalhosValidos;
    public final String delimitador;
    public final int[] indices; // [data, valor, descrição]
    public final String formatoData;

    TipoExtrato(String nome, String[] cabecalhosValidos, String delimitador, int[] indices, String formatoData) {
        this.nome = nome;
        this.cabecalhosValidos = cabecalhosValidos;
        this.delimitador = delimitador;
        this.indices = indices;
        this.formatoData = formatoData;
    }
}