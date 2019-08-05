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
    @DatabaseField(columnName = "id_instituicao_orgao")
    private Integer idInstituicaoOrgao;

    public TipoOcorrencia() {
    }

    public TipoOcorrencia(Integer id, String descricao, ClassificacaoOcorrencia classificacao) {
        this.id = id;
        this.descricao = descricao;
        this.classificacao = classificacao;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ClassificacaoOcorrencia getClassificacaoOcorrencia() {
        return classificacao;
    }

    public void setClassificacaoOcorrencia(ClassificacaoOcorrencia classificacao) {
        this.classificacao = classificacao;
    }

    public Integer getIdInstituicaoOrgao() {
        return idInstituicaoOrgao;
    }

    public void setIdInstituicaoOrgao(Integer idInstituicaoOrgao) {
        this.idInstituicaoOrgao = idInstituicaoOrgao;
    }

    public String getNomeInstituicao() {
        String instituicao = "";
        switch (idInstituicaoOrgao) {
            case 1:
                instituicao = "Corpo de Bombeiros Militar";
                break;
            case 2:
                instituicao = "Polícia Militar";
                break;
            case 3:
                instituicao = "Serviço de Atendimento Móvel de Urgência";
                break;
        }

        return instituicao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
