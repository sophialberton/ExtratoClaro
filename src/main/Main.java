package main;

import java.nio.file.Paths;

import main.controller.ExtratoController;

public class Main {

    String caminho1 = "arquivos\\extrato-exemplo.csv"; // Usando barra invertida para Windows
    // OU melhor ainda:
    String caminho2S = Paths.get("arquivos", "extrato-exemplo.csv").toString(); // Usando Paths para multiplataforma

    public static void main(String[] args) {
        new ExtratoController().processarExtrato("arquivos/extrato-exemplo.csv");
    }

}