package main.repository;

import main.model.Transacao;
import java.util.*;

public class TransacaoRepository {
    private final List<Transacao> transacoes = new ArrayList<>();

    public void salvarTodas(List<Transacao> novasTransacoes) {
        // Filtra duplicatas (simples comparação por descrição + valor)
        novasTransacoes.removeIf(nt -> transacoes.stream().anyMatch(t -> t.getDescricao().equals(nt.getDescricao()) &&
                t.getValor().compareTo(nt.getValor()) == 0));

        transacoes.addAll(novasTransacoes);
    }

    public Map<String, Double> gerarResumo() {
        Map<String, Double> resumo = new HashMap<>();
        transacoes.forEach(t -> {
            String nomeCategoria = t.getCategoria() != null ? 
                t.getCategoria().getNome() : "Outros";
            resumo.merge(nomeCategoria, t.getValor().doubleValue(), Double::sum);
        });
        return resumo;
    }
    }
