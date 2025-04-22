package main.service;

import main.model.Categoria;
import main.model.Transacao;
import java.util.*;

public class CategoriaService {
    private final Map<String, Categoria> categorias = new HashMap<>();

    public CategoriaService() {
        // Categorias adaptadas para o Nubank
        addCategoriaPadrao("Transferência", "transferência,transferencia,pix");
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
            System.out.println("\n⚠️ Nenhuma categoria cadastrada!");
            return;
        }
    
        // Lista categorias disponíveis
        System.out.println("\n📋 CATEGORIAS DISPONÍVEIS:");
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
            System.out.println("\n✏️ EDITANDO CATEGORIA: " + nomeCategoria);
            System.out.println("Palavras-chave atuais: " + String.join(", ", categoria.getPalavrasChave()));
            System.out.println("1. Adicionar palavra-chave");
            System.out.println("2. Remover palavra-chave");
            System.out.println("3. Renomear categoria");
            System.out.println("4. Voltar");
            System.out.print("Opção: ");
    
            int opcao = lerOpcaoNumerica(scanner, 1, 4);
            scanner.nextLine(); // Limpar buffer
    
            switch (opcao) {
                case 1 -> {
                    System.out.print("Nova palavra-chave: ");
                    String novaPalavra = scanner.nextLine().trim();
                    categoria.addPalavraChave(novaPalavra);
                    System.out.println("--> Palavra-chave adicionada!");
                }
                case 2 -> {
                    // Verifica se há palavras-chave
                    if (categoria.getPalavrasChave().isEmpty()) {
                        System.out.println("\nAVISO: Esta categoria nao contem palavras-chave.");
                        System.out.println("(Pressione Enter para continuar...)");
                        scanner.nextLine();
                        continue;
                    }
                
                    // Lista palavras-chave
                    System.out.println("\nPALAVRAS-CHAVE DISPONIVEIS:");
                    List<String> palavras = new ArrayList<>(categoria.getPalavrasChave());
                    
                    System.out.printf("  %2d. %-20s %s%n", 0, "[Cancelar]", "Voltar sem remover");
                    for (int i = 0; i < palavras.size(); i++) {
                        System.out.printf("  %2d. %s%n", i + 1, palavras.get(i));
                    }
                
                    // Input
                    System.out.print("\nDigite o numero da palavra para remover (0 para cancelar): ");
                    int idxPalavra = lerOpcaoNumerica(scanner, 0, palavras.size());
                    
                    if (idxPalavra == 0) {
                        System.out.println("\nOperacao cancelada.");
                        continue;
                    }
                
                    // Confirmação
                    String palavraRemovida = palavras.get(idxPalavra - 1);
                    System.out.printf("\nTem certeza que deseja remover '%s'?%n", palavraRemovida);
                    System.out.println("1. Sim, remover");
                    System.out.println("2. Nao, cancelar");
                    System.out.print("Confirmacao: ");
                    
                    int confirmacao = lerOpcaoNumerica(scanner, 1, 2);
                    
                    if (confirmacao == 1) {
                        categoria.getPalavrasChave().remove(palavraRemovida);
                        System.out.printf("\nSUCESSO: '%s' removida.%n", palavraRemovida);
                    } else {
                        System.out.println("\nOperacao cancelada.");
                    }
                    
                    System.out.println("\n(Pressione Enter para continuar...)");
                    scanner.nextLine();
                }
                case 3 -> {
                    System.out.print("Novo nome para a categoria: ");
                    String novoNome = scanner.nextLine().trim();
                    categorias.remove(nomeCategoria);
                    categorias.put(novoNome, categoria);
                    nomeCategoria = novoNome;
                    System.out.println("--> Categoria renomeada!");
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("--> Opção inválida!");
            }
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
}