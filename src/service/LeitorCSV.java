/*Lê e interpreta arquivos .csv ou .ofx.
Valida tipo de arquivo.
Dispara erro se for formato inválido. */

package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Transacao;

public class LeitorCSV {

    // Método para ler um arquivo CSV e retornar uma lista de transações
    public List<Transacao> lerCSV(String caminhoArquivo) {
        // Implementação do método para ler o arquivo CSV
        // e retornar uma lista de transações
        List<Transacao> transacoes = new ArrayList<>(); // Inicial

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))){
            String linha;
            boolean primeiraLinha = true; // Variável para verificar se é a primeira linha do arquivo
            
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    // Se a primeira linha for o cabeçalho, ignora e continua
                    primeiraLinha = false; // Ignora a primeira linha (cabeçalho)
                    continue;
                }

                String[] campos = linha.split(",");
                if (campos.length >= 5) continue;
                 
                    String data = campos[0].trim();
                    String descricao = campos[1].trim();
                    double valor = Double.parseDouble(campos[2].trim().replace(",","."));
                    String categoria = campos[3].trim();
                    String tipo = campos[4].trim(); //"entrada" ou "saida"

                    Transacao transacao = new Transacao(data, descricao, valor, categoria, tipo);

                    transacoes.add(transacao);
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler o arquivo: " + e.getMessage());
    }
    return transacoes;

    }
}