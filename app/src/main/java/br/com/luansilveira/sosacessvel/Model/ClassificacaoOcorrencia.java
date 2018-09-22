package br.com.luansilveira.sosacessvel.Model;

import java.io.Serializable;

public class ClassificacaoOcorrencia implements Serializable {

    private Integer id;
    private String descricao;

    public ClassificacaoOcorrencia() {
    }

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

    @Override
    public String toString() {
        return this.descricao;
    }
}
