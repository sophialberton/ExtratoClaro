package main.controller;

import main.service.*;
import main.repository.*;
import main.model.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExtratoController {
    private final ImportadorService importador = new ImportadorService();
    private final CategoriaService categorizador = new CategoriaService();
    private final TransacaoRepository repository = new TransacaoRepository();

public void processarExtrato(String caminhoArquivo, TipoExtrato tipo) {
    try {
        List<Transacao> transacoes = importador.importarCSV(caminhoArquivo, tipo);
        categorizador.categorizarAutomaticamente(transacoes);
        repository.salvarTodas(transacoes);
        
        System.out.println("\n=== TRANSAÇÕES IMPORTADAS ===");
        transacoes.forEach(t -> {
            String cor;
            if (tipo == TipoExtrato.NUBANK_CARTAO) {
                // Cartão de crédito - todas em vermelho
                cor = "\u001B[31m"; // Vermelho
            } else {
                // Outros extratos - verde/vermelho
                cor = t.getValor().doubleValue() < 0 ? "\u001B[31m" : "\u001B[32m";
            }
            
            System.out.printf("%s%s | %-50s | R$ %10.2f | %-15s\u001B[0m%n",
                cor,
                t.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                t.getDescricao().length() > 50 ? t.getDescricao().substring(0, 47) + "..." : t.getDescricao(),
                t.getValor(),
                (t.getCategoria() != null) ? t.getCategoria().getNome() : "Sem categoria");
        });
        
        exibirResumo();
    } catch (Exception e) {
        System.err.println("Erro ao processar extrato: " + e.getMessage());
    }
}

    public void exibirResumo() {
        System.out.println("\n=== RESUMO POR CATEGORIA ===");
        repository.gerarResumo().forEach((categoria, total) -> 
            System.out.printf("%-15s: R$ %10.2f\n", categoria, total));
    }

    public void iniciarFluxo(Scanner scanner) {
        System.out.println("\n🔷 SELECIONE O TIPO DE EXTRATO:");
        TipoExtrato[] tipos = TipoExtrato.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i].nome);
        }
        
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        
        TipoExtrato tipo = tipos[opcao - 1];
        System.out.print("📂 Caminho do arquivo CSV: ");
        String caminho = scanner.nextLine();
        
        processarExtrato(caminho, tipo);
    }
}