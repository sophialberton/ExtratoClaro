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
        addCategoriaPadrao("Alimentação", "restaurante,mercado,padaria,supermercado");
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
}