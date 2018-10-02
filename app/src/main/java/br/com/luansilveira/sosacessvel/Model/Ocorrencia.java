package br.com.luansilveira.sosacessvel.Model;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@IgnoreExtraProperties
public class Ocorrencia implements Serializable {

    private Integer id;
    private Usuario usuario;
    private TipoOcorrencia tipoOcorrencia;
    private String descricao;
    private String localizacao;
    private Double latitude;
    private Double longitude;
    private String dataOcorrencia;
    private Integer status = 1;
    private Atendente atendente;
    private String mensagemAtendente;

    @Exclude
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    @Exclude
    private String key;

    public Ocorrencia() {
        this.dataOcorrencia = dateFormat.format(new Date());
    }

    public Ocorrencia(String key, Usuario usuario, TipoOcorrencia tipoOcorrencia, String descricao,
                      String localizacao, Double latitude, Double longitude, String dataOcorrencia)
    {
        this.key = key;
        this.usuario = usuario;
        this.tipoOcorrencia = tipoOcorrencia;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.setDataOcorrencia(dataOcorrencia);
    }

    public Ocorrencia(Usuario usuario, TipoOcorrencia tipoOcorrencia, String descricao,
                      String localizacao, Double latitude, Double longitude)
    {
        this.usuario = usuario;
        this.tipoOcorrencia = tipoOcorrencia;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dataOcorrencia = dateFormat.format(new Date());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public String getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(String dataOcorrencia) {
        Date data = new Date();
        try {
            if ((!dataOcorrencia.trim().isEmpty()) && (dataOcorrencia != null)) {
                data = dateFormat.parse(dataOcorrencia);
            }
        } catch (ParseException e){
            e.printStackTrace();
        } finally {
            this.dataOcorrencia = dateFormat.format(data);
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public String getMensagemAtendente() {
        return mensagemAtendente;
    }

    public void setMensagemAtendente(String mensagemAtendente) {
        this.mensagemAtendente = mensagemAtendente;
    }

    public boolean isEnviadoFirebase(){
        return (this.key != null);
    }


    public String getDescricaoTipoOcorrencia() {
        return tipoOcorrencia.getClassificacaoOcorrencia().getId() + "." +  tipoOcorrencia.getId()
                + " - " + tipoOcorrencia.getDescricao();
    }

}
