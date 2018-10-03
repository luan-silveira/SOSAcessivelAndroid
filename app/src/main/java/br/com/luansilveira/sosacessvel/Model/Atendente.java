package br.com.luansilveira.sosacessvel.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "atendente")
public class Atendente implements Serializable{

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String nome;
    @DatabaseField
    private String instituicao;

    public Atendente() {
    }

    public Atendente(Integer id, String nome, String instituicao) {
        this.id = id;
        this.nome = nome;
        this.instituicao = instituicao;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
