package br.com.luansilveira.sosacessvel.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import br.com.luansilveira.sosacessvel.Annotations.Coluna;
import br.com.luansilveira.sosacessvel.Annotations.PrimaryKey;
import br.com.luansilveira.sosacessvel.Annotations.Tabela;

@DatabaseTable(tableName = "classificacao_ocorrencia")
public class ClassificacaoOcorrencia implements Serializable {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String descricao;

    public ClassificacaoOcorrencia() {}

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
