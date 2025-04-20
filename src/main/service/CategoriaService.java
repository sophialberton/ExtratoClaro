//@"
package main.service;

import main.model.Categoria;
import main.model.Transacao;
import java.util.*;

public class CategoriaService {
    private final Map<String, Categoria> categorias = new HashMap<>();

    public CategoriaService() {
        // Categorias padrão melhoradas
        addCategoriaPadrao("Transporte", "uber,99,táxi,taxi,transporte");
        addCategoriaPadrao("Alimentação", "giassi,supermercado,mercado,padaria,restaurante");
        addCategoriaPadrao("Renda", "salário,salario,rendimento");
        addCategoriaPadrao("Moradia", "aluguel,condomínio,luz,água,agua");
    }

    private void addCategoriaPadrao(String nome, String palavras) {
        Categoria cat = new Categoria(nome);
        Arrays.stream(palavras.split(","))
                .forEach(cat::addPalavraChave);
        categorias.put(nome, cat);
    }

    public void categorizarAutomaticamente(List<Transacao> transacoes) {
        transacoes.forEach(transacao -> {
            if (transacao.getCategoria() == null) { // Só categoriza se ainda não tiver categoria
            categorias.values().stream()
                    .filter(cat -> cat.matches(transacao.getDescricao()))
                    .findFirst()
                    .ifPresent(transacao::setCategoria);
            }
        });
    }
}
// "@ | Out-File -FilePath src/main/service/CategoriaService.java -Encoding UTF8