package br.com.luansilveira.sosacessvel.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.luansilveira.sosacessvel.Enum.Rh;
import br.com.luansilveira.sosacessvel.Enum.TipoSanguineo;

public class Usuario {

    private Integer id;
    private String nome;
    private Date dataNascimento;
    private TipoSanguineo tipoSanguineo;
    private Rh rhSanguineo;
    private String endereco;
    private String infMedicas;

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

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        try {
            this.dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
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

    public void setRhSanguineo(String rhSanguineo) {
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


}
