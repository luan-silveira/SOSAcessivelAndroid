package br.com.luansilveira.sosacessvel.Enum;

public enum Rh {

    P("Positivo"),
    N("Negativo");

    private String descricao;

    Rh(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
