package br.com.luansilveira.sosacessvel.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Ocorrencia {

    private String key;
    private Usuario paciente;
    private TipoOcorrencia tipoOcorrencia;
    private String descricao;
    private String localizacao;
    private Double latitude;
    private Double longitude;
    private Date dataOcorrencia;

    public Ocorrencia() {
    }

    public Ocorrencia(String key, Usuario paciente, TipoOcorrencia tipoOcorrencia, String descricao,
                      String localizacao, Double latitude, Double longitude, Date dataOcorrencia)
    {
        this.key = key;
        this.paciente = paciente;
        this.tipoOcorrencia = tipoOcorrencia;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.setDataOcorrencia(dataOcorrencia);
    }

    public Ocorrencia(Usuario paciente, TipoOcorrencia tipoOcorrencia, String descricao,
                      String localizacao, Double latitude, Double longitude)
    {
        this.paciente = paciente;
        this.tipoOcorrencia = tipoOcorrencia;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dataOcorrencia = new Date();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Usuario getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario paciente) {
        this.paciente = paciente;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia == null ? new Date() : dataOcorrencia;
    }
}
