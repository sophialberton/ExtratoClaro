package main.service;

import main.model.Categoria;
import main.model.Transacao;
import java.util.*;

public class CategoriaService {
    private final Map<String, Categoria> categorias = new HashMap<>();

    public CategoriaService() {
        // Categorias adaptadas para o Nubank
        addCategoriaPadrao("Transf. Contas", "Sophia");
        addCategoriaPadrao("Farmácia", "drogaria,farmácia,farmacia,raia,drogasil,drogaria catur,drogaria catarinense");
        addCategoriaPadrao("Pagamento", "pagamento,fatura");
        addCategoriaPadrao("Alimentação", "restaurante,mercado,padaria,supermercado,sabor,alimentos");
        addCategoriaPadrao("Transporte", "99app,uber");

    }

    private void addCategoriaPadrao(String nome, String palavras) {
        Categoria cat = new Categoria(nome);
        Arrays.stream(palavras.split(","))
            .forEach(cat::addPalavraChave);
        categorias.put(nome, cat);
    }

    public void categorizarAutomaticamente(List<Transacao> transacoes) {
        transacoes.forEach(transacao -> {
            // Transforma descrição em minúsculas para comparação case-insensitive
            String descricao = transacao.getDescricao().toLowerCase();
            
            categorias.values().stream()
                .filter(cat -> cat.getPalavrasChave().stream()
                    .anyMatch(palavra -> descricao.contains(palavra)))
                .findFirst()
                .ifPresent(transacao::setCategoria);
        });
    }

    public void adicionarCategoriaManual(Scanner scanner) {
        System.out.println("\n=== CRIAR NOVA CATEGORIA===");
        
        System.out.print("Nome da categoria: ");
        String nome = scanner.nextLine();
        
        System.out.print("Palavras-chave (separadas por vírgula): ");
        String palavrasInput = scanner.nextLine();
        
        Categoria novaCategoria = new Categoria(nome);
        Arrays.stream(palavrasInput.split(","))
              .map(String::trim)
              .filter(p -> !p.isEmpty())
              .forEach(novaCategoria::addPalavraChave);
        
        categorias.put(nome, novaCategoria);
        System.out.println("--> Categoria '" + nome + "' adicionada com sucesso!");
    }
    
    public void editarCategoria(Scanner scanner) {
        if (categorias.isEmpty()) {
            System.out.println("\n-== Nenhuma categoria cadastrada! ==-");
            return;
        }
    
        // Lista categorias disponíveis
        System.out.println("\n-== CATEGORIAS DISPONÍVEIS==-:");
        List<String> nomesCategorias = new ArrayList<>(categorias.keySet());
        
        for (int i = 0; i < nomesCategorias.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, nomesCategorias.get(i));
        }
    
        // Seleção segura da categoria
        int escolha = lerOpcaoNumerica(scanner, 1, nomesCategorias.size());
        String nomeCategoria = nomesCategorias.get(escolha - 1);
        Categoria categoria = categorias.get(nomeCategoria);
    
        // Menu de edição
        while (true) {
            System.out.println("\nEDITANDO CATEGORIA: " + nomeCategoria);
            System.out.println("Palavras-chave atuais: " + String.join(", ", categoria.getPalavrasChave()));
            System.out.println("1. Adicionar palavra-chave");
            System.out.println("2. Remover palavra-chave");
            System.out.println("3. Renomear categoria");
            System.out.println("4. Apagar esta categoria");
            System.out.println("5. Voltar");
            System.out.print("Opção: ");
            
            int opcao = lerOpcaoNumerica(scanner, 1, 5);
    
            switch (opcao) {
                case 1 -> adicionarPalavraChave(scanner, categoria);
                case 2 -> removerPalavraChave(scanner, categoria);
                case 3 -> renomearCategoria(scanner, nomeCategoria, categoria);
                case 4 -> {
                    if (confirmarRemocao(scanner, "Tem certeza que deseja apagar esta categoria?")) {
                        categorias.remove(nomeCategoria);
                        System.out.println("Categoria removida com sucesso!");
                        return;
                    }
                }
                case 5 -> { return; }
            }
        }
    }
    
    // Métodos auxiliares separados para melhor organização
    private void adicionarPalavraChave(Scanner scanner, Categoria categoria) {
        System.out.print("\nNova palavra-chave: ");
        String novaPalavra = scanner.nextLine().trim();
        categoria.addPalavraChave(novaPalavra);
        System.out.println("Palavra-chave adicionada!");
    }
    
    private void removerPalavraChave(Scanner scanner, Categoria categoria) {
        if (categoria.getPalavrasChave().isEmpty()) {
            System.out.println("\nEsta categoria não tem palavras-chave!");
            return;
        }
        
        System.out.println("\nPalavras-chave disponíveis:");
        List<String> palavras = new ArrayList<>(categoria.getPalavrasChave());
        for (int i = 0; i < palavras.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, palavras.get(i));
        }
        
        System.out.print("\nDigite o número da palavra para remover (0 para cancelar): ");
        int idxPalavra = lerOpcaoNumerica(scanner, 0, palavras.size());
        
        if (idxPalavra > 0) {
            String palavraRemovida = palavras.get(idxPalavra - 1);
            categoria.getPalavrasChave().remove(palavraRemovida);
            System.out.printf("Palavra-chave '%s' removida!%n", palavraRemovida);
        }
    }
    
    private void renomearCategoria(Scanner scanner, String nomeAtual, Categoria categoria) {
        System.out.print("\nNovo nome para a categoria: ");
        String novoNome = scanner.nextLine().trim();
        
        if (!novoNome.isEmpty() && !categorias.containsKey(novoNome)) {
            categorias.remove(nomeAtual);
            categorias.put(novoNome, categoria);
            System.out.println("Categoria renomeada com sucesso!");
        } else {
            System.out.println("Nome inválido ou já existente!");
        }
    }
    
    private boolean confirmarRemocao(Scanner scanner, String mensagem) {
        System.out.printf("\n%s%n", mensagem);
        System.out.println("1. Sim");
        System.out.println("2. Não");
        System.out.print("Confirmação: ");
        return lerOpcaoNumerica(scanner, 1, 2) == 1;
    }

    public void removerCategoria(Scanner scanner) {
        if (categorias.isEmpty()) {
            System.out.println("\nNenhuma categoria cadastrada!");
            return;
        }
    
        System.out.println("\nCATEGORIAS CADASTRADAS:");
        List<String> nomesCategorias = new ArrayList<>(categorias.keySet());
        for (int i = 0; i < nomesCategorias.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, nomesCategorias.get(i));
        }
    
        System.out.print("\nDigite o número da categoria para remover (0 para cancelar): ");
        int escolha = lerOpcaoNumerica(scanner, 0, nomesCategorias.size());
        
        if (escolha == 0) {
            System.out.println("Operação cancelada.");
            return;
        }
    
        String nomeCategoria = nomesCategorias.get(escolha - 1);
        
        System.out.printf("\nTem certeza que deseja remover a categoria '%s'?%n", nomeCategoria);
        System.out.println("1. Sim, remover");
        System.out.println("2. Não, cancelar");
        System.out.print("Confirmação: ");
        
        int confirmacao = lerOpcaoNumerica(scanner, 1, 2);
        
        if (confirmacao == 1) {
            categorias.remove(nomeCategoria);
            System.out.printf("Categoria '%s' removida com sucesso!%n", nomeCategoria);
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private int lerOpcaoNumerica(Scanner scanner, int minimo, int maximo) {
        while (true) {
            try {
                System.out.print("Escolha uma opção (" + minimo + "-" + maximo + "): ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
                
                if (opcao >= minimo && opcao <= maximo) {
                    return opcao;
                } else {
                    System.out.println("--> Por favor, digite um número entre " + minimo + " e " + maximo);
                }
            } catch (InputMismatchException e) {
                System.out.println("--> Entrada inválida! Digite apenas números.");
                scanner.nextLine(); // Limpar input incorreto
            }
        }
    }

    public void listarCategorias() {
        if (categorias.isEmpty()) {
            System.out.println("\nNenhuma categoria cadastrada.");
            return;
        }
        
        System.out.println("\nLISTA DE CATEGORIAS:");
        categorias.forEach((nome, categoria) -> {
            System.out.println("- " + nome + ": " + 
                String.join(", ", categoria.getPalavrasChave()));
        });
        
        System.out.println("\nTotal: " + categorias.size() + " categorias cadastradas.");
    }
}