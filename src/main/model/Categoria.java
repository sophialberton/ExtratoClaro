//@"
package main.model;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private String nome;
    private List<String> palavrasChave;

    public Categoria(String nome) {
        this.nome = nome;
        this.palavrasChave = new ArrayList<>();
    }

    public void addPalavraChave(String palavra) {
        palavrasChave.add(palavra.toLowerCase());
    }

    public boolean matches(String descricao) {
        return palavrasChave.stream()
            .anyMatch(p -> descricao.toLowerCase().contains(p));
    }

    // Getters
    public String getNome() { return nome; }
    public List<String> getPalavrasChave() { return palavrasChave; }
}
//"@ | Out-File -FilePath src/main/model/Categoria.java -Encoding UTF8