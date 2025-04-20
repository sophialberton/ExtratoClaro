package main;

import main.controller.ExtratoController;

public class Main {
    public static void main(String[] args) {
        new ExtratoController().processarExtrato("arquivos/extrato-exemplo.csv");
    }
}