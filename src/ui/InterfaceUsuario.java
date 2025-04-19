/*Responsável por interações com 
o usuário no terminal.
Exibe mensagens, opções e resumos.
Solicita confirmação de categorização. */

package ui;

import model.Transacao;
import java.util.List;

public class InterfaceUsuario {
    
    // Método para exibir o menu principal
    public static void exibirTransacoes(List<Transacao> transacoes) {
        System.out.println("=== Transacoes ===");
        for (Transacao t : transacoes) {
            System.out.println("Data: " + t.getData() + ", Descrição: " + t.getDescricao() +
                    ", Valor: " + t.getValor() + ", Categoria: " + t.getCategoria() +
                    ", Tipo: " + t.getTipo());
        }
    }
}