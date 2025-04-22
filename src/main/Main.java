package main;

//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.util.Scanner;
import main.controller.ExtratoController;
//import main.model.TipoExtrato;

public class Main {
    public static void main(String[] args) {
        ExtratoController controller = new ExtratoController();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1. Importar extrato");
                System.out.println("2. Ver resumo");
                System.out.println("3. Criar categoria");
                System.out.println("4. Editar categoria");
                System.out.println("5. Sair");
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
                    case 3 -> controller.getCategorizador().adicionarCategoriaManual(scanner);
                    case 4 -> controller.getCategorizador().editarCategoria(scanner);
                    case 5 -> {
                        System.out.println("Encerrando o sistema...");
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            }
        }
    }
}