/*Responsável por iniciar a aplicação
Chama os métodos de importação de extrato e faz
a integração entre os componentes. */

// src/Main.java
import model.Categoria;
import model.Transacao;
import service.LeitorCSV;
import ui.InterfaceUsuario;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LeitorCSV leitor = new LeitorCSV();
        List<Transacao> transacoes = leitor.lerCSV("cd../arquivos/ExtratoNU03-2025.csv");

        InterfaceUsuario.exibirTransacoes(transacoes);

        System.out.println("\n=== Criar nova categoria ===");
        System.out.print("Nome da categoria: ");
        String nomeCategoria = scanner.nextLine();

        System.out.print("Palavras-chave separadas por vírgula: ");
        String[] palavras = scanner.nextLine().split(",");

        Categoria novaCategoria = new Categoria(nomeCategoria, palavras, true);

        // Exemplo de vinculação simples (usando palavras-chave)
        for (Transacao t : transacoes) {
            for (String palavra : palavras) {
                if (t.getDescricao().toLowerCase().contains(palavra.trim().toLowerCase())) {
                    // Aqui você poderia criar lógica para armazenar essa relação de forma mais robusta
                    System.out.println("→ Categoria atribuída a: " + t.getDescricao());
                }
            }
        }

        // Reexibe as transações depois da categorização (opcional)
        // InterfaceUsuario.exibirTransacoes(transacoes);
        scanner.close();

    }
}
