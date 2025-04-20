//@"
package main.service;

import main.model.Transacao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ImportadorService {
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
                
                String[] campos = linha.split(",");
                transacoes.add(new Transacao(
                    LocalDate.parse(campos[0].trim()),
                    campos[1].trim(),
                    new BigDecimal(campos[2].trim())
                ));
            }
        }
        return transacoes;
    }
}
//"@ | Out-File -FilePath src/main/service/ImportadorService.java -Encoding UTF8