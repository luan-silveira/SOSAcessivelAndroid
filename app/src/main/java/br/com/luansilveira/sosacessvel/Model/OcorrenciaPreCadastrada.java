package br.com.luansilveira.sosacessvel.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "ocorrencia_precadastrada")
public class OcorrenciaPreCadastrada implements Serializable {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "id_tipo_ocorrencia", foreign = true, foreignColumnName = "id")
    private TipoOcorrencia tipoOcorrencia;
    @DatabaseField
    private String descricao;
    @DatabaseField
    private String localizacao;

    public OcorrenciaPreCadastrada() {
    }

    public OcorrenciaPreCadastrada(TipoOcorrencia tipoOcorrencia, String descricao, String localizacao) {
        this.tipoOcorrencia = tipoOcorrencia;
        this.descricao = descricao;
        this.localizacao = localizacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoOcorrencia getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
