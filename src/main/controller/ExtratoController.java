package main.controller;

import main.service.*;
import main.repository.*;
import main.model.*;
import java.util.*;

public class ExtratoController {
    private final ImportadorService importador = new ImportadorService();
    private final CategoriaService categorizador = new CategoriaService();
    private final TransacaoRepository repository = new TransacaoRepository();
    // private List<Transacao> transacoes; // Mova para nível de classe

    public void processarExtrato(String caminhoArquivo) {
        try {
            List<Transacao> transacoes = importador.importarCSV(caminhoArquivo); // Atribui à variável de classe
            categorizador.categorizarAutomaticamente(transacoes);
            repository.salvarTodas(transacoes);

            System.out.println("\n=== TRANSAÇÕES IMPORTADAS ===");
            transacoes.forEach(t -> {
                String cor = t.getValor().doubleValue() < 0 ? "\u001B[31m" : "\u001B[32m";
                System.out.printf("%s%s | %-20s | R$ %8.2f | %-15s\u001B[0m%n",
                        cor,
                        t.getData(),
                        t.getDescricao(),
                        t.getValor(),
                        (t.getCategoria() != null) ? t.getCategoria().getNome() : "Sem categoria");
            });

            exibirResumo(); // Chama o método de exibição

        } catch (Exception e) {
            System.err.println("Erro ao processar extrato: " + e.getMessage());
        }
    }

    public void exibirResumo() {
        System.out.println("\n=== RESUMO POR CATEGORIA ===");
        repository.gerarResumo().forEach((categoria, total) -> {
            String cor = total < 0 ? "\u001B[31m" : "\u001B[32m";
            System.out.printf("%-15s: %sR$ %10.2f\u001B[0m%n",
                    categoria, cor, total);
        });
    }
}