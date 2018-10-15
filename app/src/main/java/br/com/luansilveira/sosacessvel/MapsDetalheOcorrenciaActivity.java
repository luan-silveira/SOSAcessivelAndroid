package br.com.luansilveira.sosacessvel;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.luansilveira.sosacessvel.Model.Ocorrencia;

public class MapsDetalheOcorrenciaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView txtTipoOcorrencia;
    private TextView txtClassificacaoOcorrencia;
    private TextView txtStatus;
    private TextView txtDescricaoOcorrencia;
    private TextView txtDescricaoLocalizacao;
    private TextView txtAtendente;
    private TextView txtInstituicaoAtendimento;
    private TextView txtMensagemAtendente;

    private Ocorrencia ocorrencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_detalhe_ocorrencia);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detalhes da Ocorrência");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.carregarObjetos();

        ocorrencia = (Ocorrencia) getIntent().getExtras().getSerializable("ocorrencia");

        LinearLayout layoutAtendimento = findViewById(R.id.layoutAtendimentoOcorrencia);

        txtTipoOcorrencia.setText(ocorrencia.getTipoOcorrencia().toString());
        txtClassificacaoOcorrencia.setText(ocorrencia.getTipoOcorrencia().getClassificacaoOcorrencia().toString());
        txtStatus.setText(ocorrencia.getDescricaoStatus());
        txtDescricaoOcorrencia.setText(ocorrencia.getDescricao());
        txtDescricaoLocalizacao.setText(ocorrencia.getLocalizacao());
        if (ocorrencia.getAtendente() != null) {
            txtInstituicaoAtendimento.setText(ocorrencia.getAtendente().getInstituicao());
            txtAtendente.setText(ocorrencia.getAtendente().getNome());
            txtMensagemAtendente.setText(ocorrencia.getMensagemAtendente());
        } else {
            layoutAtendimento.setVisibility(View.GONE);
        }



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng coordenadas = new LatLng(ocorrencia.getLatitude(), ocorrencia.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 16));
        mMap.addMarker(new MarkerOptions().position(coordenadas));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void carregarObjetos(){
        txtTipoOcorrencia = (TextView) findViewById(R.id.txt_detalhe_tipoOcorrencia);
        txtClassificacaoOcorrencia = (TextView) findViewById(R.id.txt_detalhe_classificacaoOcorrencia);
        txtStatus = (TextView) findViewById(R.id.txt_detalhe_status);
        txtDescricaoOcorrencia = (TextView) findViewById(R.id.txt_detalhe_descricao);
        txtDescricaoLocalizacao = (TextView) findViewById(R.id.txt_detalhe_desc_localizacao);
        txtAtendente = (TextView) findViewById(R.id.txt_detalhe_atendente);
        txtInstituicaoAtendimento = (TextView) findViewById(R.id.txt_instituicao_atendimento);
        txtMensagemAtendente = (TextView) findViewById(R.id.txt_detalhe_mensagemAtendente);
    }

}
