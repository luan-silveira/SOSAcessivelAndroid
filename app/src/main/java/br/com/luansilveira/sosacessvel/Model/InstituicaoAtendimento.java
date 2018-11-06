package br.com.luansilveira.sosacessvel.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "instituicao_atendimento")
public class InstituicaoAtendimento implements Serializable {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String nome;
    @DatabaseField(columnName = "id_instituicao_orgao")
    private Integer idInstituicaoOrgao;

    public InstituicaoAtendimento() {
    }

    public InstituicaoAtendimento(Integer id, String nome, Integer idInstituicaoOrgao) {
        this.id = id;
        this.nome = nome;
        this.idInstituicaoOrgao = idInstituicaoOrgao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdInstituicaoOrgao(Integer idInstituicaoOrgao) {
        this.idInstituicaoOrgao = idInstituicaoOrgao;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdInstituicaoOrgao() {
        return idInstituicaoOrgao;
    }

    public String getNomeInstituicao(){
        String instituicao = new String();
        switch (idInstituicaoOrgao){
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
        return this.nome;
    }
}
