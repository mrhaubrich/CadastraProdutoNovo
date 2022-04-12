package models;

public class Produto extends DBItem {
    private String nome;
    private String descricao;

    public Produto(int codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }
}
