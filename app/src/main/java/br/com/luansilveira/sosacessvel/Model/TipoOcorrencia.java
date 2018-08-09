package br.com.luansilveira.sosacessvel.Model;

public class TipoOcorrencia {

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
}
