package main;

import java.util.InputMismatchException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.Scanner;
import main.controller.ExtratoController;
//import main.model.TipoExtrato;
import main.service.CategoriaService;

public class Main {
    public static void main(String[] args) {
        ExtratoController controller = new ExtratoController();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1. Importar extrato");
                System.out.println("2. Ver resumo");
                System.out.println("3. Gerenciar categorias");
                System.out.println("4. Sair");
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
                    case 1 -> controller.iniciarFluxo(scanner);
                    case 2 -> controller.exibirResumo();
                    case 3 -> exibirMenuCategorias(scanner, controller);
                    case 4 -> {
                        System.out.println("Encerrando o sistema...");
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            }
        }
    }

    private static void exibirMenuCategorias(Scanner scanner, ExtratoController controller) {
    CategoriaService categorizador = controller.getCategorizador();
    
    while (true) {
        System.out.println("\nGERENCIAR CATEGORIAS:");
        System.out.println("1. Criar nova categoria");
        System.out.println("2. Editar categoria existente");
        System.out.println("3. Remover categoria");
        System.out.println("4. Listar todas as categorias");
        System.out.println("5. Voltar ao menu principal");
        System.out.print("Opção: ");
        
        int opcao = lerOpcaoNumerica(scanner, 1, 5);
        
        switch (opcao) {
            case 1 -> categorizador.adicionarCategoriaManual(scanner);
            case 2 -> categorizador.editarCategoria(scanner);
            case 3 -> categorizador.removerCategoria(scanner);
            case 4 -> categorizador.listarCategorias();
            case 5 -> { return; }
        }
    }
}

// Método auxiliar para leitura segura de números
private static int lerOpcaoNumerica(Scanner scanner, int min, int max) {
    while (true) {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            if (opcao >= min && opcao <= max) {
                return opcao;
            }
            System.out.printf("Digite um número entre %d e %d: ", min, max);
        } catch (InputMismatchException e) {
            System.out.print("Entrada inválida. Digite um número: ");
            scanner.nextLine(); // Limpar input inválido
        }
    }
}
}