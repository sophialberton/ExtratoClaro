package main.service;

import main.model.TipoExtrato;
import main.model.Transacao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ImportadorService {

    public List<Transacao> importarCSV(String caminho, TipoExtrato tipo) throws Exception {
        List<Transacao> transacoes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            boolean cabecalhoValido = false;

            while ((linha = br.readLine()) != null) {
                if (!cabecalhoValido) {
                    cabecalhoValido = validarCabecalho(linha, tipo);
                    continue;
                }

                String[] campos = processarLinha(linha, tipo);
                Transacao transacao = criarTransacao(campos, tipo);
                if (transacao != null) {
                    transacoes.add(transacao);
                }
            }
        }
        return transacoes;
    }

    private String[] processarLinha(String linha, TipoExtrato tipo) {
        if (tipo == TipoExtrato.MERCADO_PAGO) {
            linha = linha.replaceAll("^\"|\"$", ""); // Remove aspas no início e fim
        }
        return linha.split(tipo.delimitador + "(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    }

    private Transacao criarTransacao(String[] campos, TipoExtrato tipo) {
        try {
            LocalDate data = parseData(campos[tipo.indices[0]].trim(), tipo);
            BigDecimal valor = parseValor(campos[tipo.indices[1]].trim());
            String descricao = campos[tipo.indices[2]].trim();

            return new Transacao(data, descricao, valor);
        } catch (Exception e) {
            System.err.println("Erro ao processar linha: " + e.getMessage());
            return null;
        }
    }

    private LocalDate parseData(String dataStr, TipoExtrato tipo) {
        if (tipo == TipoExtrato.MERCADO_PAGO) {
            return LocalDateTime.parse(dataStr, DateTimeFormatter.ofPattern(tipo.formatoData)).toLocalDate();
        }
        return LocalDate.parse(dataStr, DateTimeFormatter.ofPattern(tipo.formatoData));
    }

    private BigDecimal parseValor(String valorStr) {
        // Remove R$, aspas e formata decimais corretamente
        String valorLimpo = valorStr.replace("R$", "")
                .replace("\"", "")
                .replace("", "")
                .trim();

        // Verifica se usa ponto como separador decimal
        if (valorLimpo.contains(".") && valorLimpo.contains(",")) {
            // Formato 1.000,00 (remove pontos de milhar)
            valorLimpo = valorLimpo.replace(".", "").replace(",", ".");
        } else if (valorLimpo.contains(",")) {
            // Formato 1000,00 (apenas substitui vírgula)
            valorLimpo = valorLimpo.replace(",", ".");
        }
        // Se já estiver com ponto decimal, mantém

        return new BigDecimal(valorLimpo);
    }

    private boolean validarCabecalho(String linha, TipoExtrato tipo) {
        return Arrays.stream(tipo.cabecalhosValidos)
                .anyMatch(cabecalho -> linha.trim().equalsIgnoreCase(cabecalho));
    }

}