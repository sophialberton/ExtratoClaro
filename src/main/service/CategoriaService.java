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
    
        // Seleção da categoria
        System.out.print("\nEscolha o número da categoria para editar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
    
        if (escolha < 1 || escolha > nomesCategorias.size()) {
            System.out.println("❌ Opção inválida!");
            return;
        }
    
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
    
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
    
            switch (opcao) {
                case 1 -> {
                    System.out.print("Nova palavra-chave: ");
                    String novaPalavra = scanner.nextLine().trim();
                    categoria.addPalavraChave(novaPalavra);
                    System.out.println("✅ Palavra-chave adicionada!");
                }
                case 2 -> {
                    System.out.print("Palavra-chave para remover: ");
                    String palavraRemover = scanner.nextLine().trim();
                    if (categoria.getPalavrasChave().remove(palavraRemover)) {
                        System.out.println("✅ Palavra-chave removida!");
                    } else {
                        System.out.println("❌ Palavra não encontrada!");
                    }
                }
                case 3 -> {
                    System.out.print("Novo nome para a categoria: ");
                    String novoNome = scanner.nextLine().trim();
                    categorias.remove(nomeCategoria);
                    categorias.put(novoNome, categoria);
                    nomeCategoria = novoNome;
                    System.out.println("✅ Categoria renomeada!");
                }
                case 4 -> {
                    return;
                }
                default -> System.out.println("❌ Opção inválida!");
            }
        }
    }
}