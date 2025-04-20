package main;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import main.controller.ExtratoController;

public class Main {

    String caminho1 = "arquivos\\extrato-exemplo.csv"; // Usando barra invertida para Windows
    // OU melhor ainda:
    String caminho2S = Paths.get("arquivos", "extrato-exemplo.csv").toString(); // Usando Paths para multiplataforma

    public static void main(String[] args) {
        ExtratoController controller = new ExtratoController();

        // Usando try-with-resources para fechar o Scanner automaticamente
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1. Importar extrato");
                System.out.println("2. Ver resumo");
                System.out.println("3. Sair");
                System.out.print("Opção: ");

                int opcao = 0;
                try {
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer
                } catch (Exception e) {
                    System.out.println("Entrada inválida! Digite um número.");
                    scanner.nextLine(); // Limpar buffer inválido
                    continue;
                }

                switch (opcao) {
                    case 1 -> {
                        System.out.print("Caminho do arquivo (ex: arquivos/extrato.csv): ");
                        String caminho = scanner.nextLine();
                        if (!Files.exists(Paths.get(caminho))) {
                            System.out.println("Arquivo não encontrado!");
                            continue;
                        }
                        controller.processarExtrato(caminho);
                    }
                    case 2 -> controller.exibirResumo();
                    case 3 -> {
                        System.out.println("Encerrando o sistema...");
                        return; // Mais elegante que System.exit()
                    }
                    default -> System.out.println("Opção inválida!");
                }
            }
        }
    }

}