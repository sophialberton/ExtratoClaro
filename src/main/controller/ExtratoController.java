package main.controller;

import main.service.*;
import main.repository.*;
import main.model.*;
import java.util.*;

public class ExtratoController {
    private final ImportadorService importador = new ImportadorService();
    private final CategoriaService categorizador = new CategoriaService();
    private final TransacaoRepository repository = new TransacaoRepository();
    private List<Transacao> transacoes; // Mova para nível de classe

    public void processarExtrato(String caminhoArquivo) {
        try {
            this.transacoes = importador.importarCSV(caminhoArquivo); // Atribui à variável de classe
            categorizador.categorizarAutomaticamente(transacoes);
            repository.salvarTodas(transacoes);
            
            System.out.println("\n=== TRANSAÇÕES IMPORTADAS ===");
            transacoes.forEach(System.out::println);
            
            exibirResumo(); // Chama o método de exibição
            
        } catch (Exception e) {
            System.err.println("Erro ao processar extrato: " + e.getMessage());
        }
    }

    public void exibirResumo() {
        System.out.println("\n=== RESUMO POR CATEGORIA ===");
        repository.gerarResumo().forEach((categoria, total) -> 
            System.out.printf("%-15s: R$ %10.2f\n", categoria, total));
    }
}