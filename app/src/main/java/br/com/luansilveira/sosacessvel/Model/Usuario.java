package br.com.luansilveira.sosacessvel.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.luansilveira.sosacessvel.Enum.Rh;
import br.com.luansilveira.sosacessvel.Enum.TipoSanguineo;

public class Usuario implements Serializable {

    @Exclude
    private String key;

    private Integer id;
    private String nome;
    private Date dataNascimento;
    private TipoSanguineo tipoSanguineo;
    private Rh rhSanguineo;
    private String endereco;
    private String infMedicas;

    public Usuario() {
    }

    public Usuario(int id, String nome, Date dataNascimento, TipoSanguineo tipoSanguineo, Rh rhSanguineo, String endereco, String infMedicas) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.tipoSanguineo = tipoSanguineo;
        this.rhSanguineo = rhSanguineo;
        this.endereco = endereco;
        this.infMedicas = infMedicas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getDataNascimentoString() {
        return (new SimpleDateFormat("dd/MM/yyyy")).format(dataNascimento);
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setDataNascimentoString(String dataNascimento) {
        try {
            this.dataNascimento = (new SimpleDateFormat("dd/MM/yyyy")).parse(dataNascimento);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = TipoSanguineo.valueOf(tipoSanguineo);
    }

    public Rh getRhSanguineo() {
        return rhSanguineo;
    }

    public void setRhSanguineo(Rh rhSanguineo) {
        this.rhSanguineo = rhSanguineo;
    }

    public void setRhSanguineoString(String rhSanguineo) {
        this.rhSanguineo = Rh.valueOf(rhSanguineo);
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getInfMedicas() {
        return infMedicas;
    }

    public void setInfMedicas(String infMedicas) {
        this.infMedicas = infMedicas;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
