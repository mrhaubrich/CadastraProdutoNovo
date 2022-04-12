package models;

public class Produto {
    
    private int codigo;
    private String nome;
    private String descricao;

    public Produto(int codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }
}
