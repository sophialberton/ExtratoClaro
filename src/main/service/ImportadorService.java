package main.service;

import main.model.Transacao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImportadorService {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<Transacao> importarCSV(String caminho) throws Exception {
        List<Transacao> transacoes = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            boolean cabecalho = true;
            
            while ((linha = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }
                
                String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Regex para lidar com vírgulas na descrição
                
                LocalDate data = LocalDate.parse(campos[0].trim(), FORMATO_DATA);
                BigDecimal valor = new BigDecimal(campos[1].trim());
                String descricao = campos[3].trim();
                
                transacoes.add(new Transacao(data, descricao, valor));
            }
        }
        return transacoes;
    }
}