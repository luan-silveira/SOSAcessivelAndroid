package br.com.luansilveira.sosacessvel;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.luansilveira.sosacessvel.Model.Ocorrencia;

public class DetalheOcorrenciaActivity extends AppCompatActivity {

    private TextView txtTipoOcorrencia;
    private TextView txtClassificacaoOcorrencia;
    private TextView txtStatus;
    private TextView txtDescricaoOcorrencia;
    private TextView txtDescricaoLocalizacao;
    private TextView txtAtendente;
    private TextView txtMensagemAtendente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_ocorrencia);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detalhes da Ocorrência");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.carregarObjetos();

        String status = new String();
        Ocorrencia ocorrencia = (Ocorrencia) getIntent().getExtras().getSerializable("ocorrencia");

        switch(ocorrencia.getStatus()){
            case 1:
                status = "Aguardando atendimento";
                break;
            case 2:
                status = "Atendida";
                break;
            case 3:
                status = "Socorro enviado";
                break;
            case 4:
                status = "Finalizada";
                break;
        }

        txtTipoOcorrencia.setText(ocorrencia.getTipoOcorrencia().toString());
        txtClassificacaoOcorrencia.setText(ocorrencia.getTipoOcorrencia().getClassificacaoOcorrencia().toString());
        txtStatus.setText(status);
        txtDescricaoOcorrencia.setText(ocorrencia.getDescricao());
        txtDescricaoLocalizacao.setText(ocorrencia.getLocalizacao());
        if (ocorrencia.getAtendente() != null) txtAtendente.setText(ocorrencia.getAtendente().getNome());
        txtMensagemAtendente.setText(ocorrencia.getMensagemAtendente());
    }

    protected void carregarObjetos(){
        txtTipoOcorrencia = findViewById(R.id.txt_detalhe_tipoOcorrencia);
        txtClassificacaoOcorrencia = findViewById(R.id.txt_detalhe_classificacaoOcorrencia);
        txtStatus = findViewById(R.id.txt_detalhe_status);
        txtDescricaoOcorrencia = findViewById(R.id.txt_detalhe_descricao);
        txtDescricaoLocalizacao = findViewById(R.id.txt_detalhe_desc_localizacao);
        txtAtendente = findViewById(R.id.txt_detalhe_atendente);
        txtMensagemAtendente = findViewById(R.id.txt_detalhe_mensagemAtendente);
    }


}
