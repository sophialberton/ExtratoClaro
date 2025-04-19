/*Representa uma categoria criada pelo usuário 
ou padrão do sistema.
Campos: nome, palavrasChave, é Personalizada, etc. */

package model;

class Categoria {

    private String nome;
    private String[] palavrasChave;
    private boolean personalizada;

    public Categoria(String nome, String[] palavrasChave, boolean personalizada) {
        this.nome = nome;
        this.palavrasChave = palavrasChave;
        this.personalizada = personalizada;
    }

    public String getNome() {
        return nome;
    }

    public String[] getPalavrasChave() {
        return palavrasChave;
    }

    public boolean isPersonalizada() {
        return personalizada;
    }
}