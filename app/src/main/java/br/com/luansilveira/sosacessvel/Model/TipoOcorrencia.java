package br.com.luansilveira.sosacessvel.Model;

import java.io.Serializable;

public class TipoOcorrencia implements Serializable {

    private Integer id;
    private String descricao;
    private ClassificacaoOcorrencia classificacao;

    public TipoOcorrencia() {
    }

    public TipoOcorrencia(Integer id, String descricao, ClassificacaoOcorrencia classificacao) {
        this.id = id;
        this.descricao = descricao;
        this.classificacao = classificacao;
    }

    public Integer getId(){
        return this.id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ClassificacaoOcorrencia getClassificacaoOcorrencia() {
        return classificacao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setClassificacaoOcorrencia(ClassificacaoOcorrencia classificacao) {
        this.classificacao = classificacao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
