package br.com.luansilveira.sosacessvel.Model;

public class ClassificacaoOcorrencia {

    private Integer id;
    private String descricao;

    public ClassificacaoOcorrencia(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
