package br.com.luansilveira.sosacessvel.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tipo_ocorrencia")
public class TipoOcorrencia implements Serializable {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String descricao;
    @DatabaseField(columnName = "id_classificacao_ocorrencia", foreign = true, foreignColumnName = "id")
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
