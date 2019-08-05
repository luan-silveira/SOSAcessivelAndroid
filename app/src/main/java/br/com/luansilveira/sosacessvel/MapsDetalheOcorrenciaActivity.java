package br.com.luansilveira.sosacessvel;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    private TextView txtInstituicaoAtendimento;
    private TextView txtDataAtendimento;
    private TextView txtMensagemAtendente;

    private Ocorrencia ocorrencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_detalhe_ocorrencia);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        ocorrencia = (Ocorrencia) getIntent().getExtras().getSerializable("ocorrencia");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detalhes da Ocorrência");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.carregarObjetos();


        LinearLayout layoutAtendimento = findViewById(R.id.layoutAtendimentoOcorrencia);

        txtTipoOcorrencia.setText(ocorrencia.getTipoOcorrencia().toString());
        txtClassificacaoOcorrencia.setText(ocorrencia.getTipoOcorrencia().getClassificacaoOcorrencia().toString());
        txtStatus.setText(ocorrencia.getDescricaoStatus());
        txtDescricaoOcorrencia.setText(ocorrencia.getDescricao());
        txtDescricaoLocalizacao.setText(ocorrencia.getLocalizacao());
        if (ocorrencia.getInstituicao() != null) {
            txtInstituicaoAtendimento.setText(ocorrencia.getInstituicao().getNome());
            txtDataAtendimento.setText(ocorrencia.getDataAtendimento());
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
        LatLng coordenadas = null;
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ocorrencia.getLatitude() != null && ocorrencia.getLongitude() != null) {
            coordenadas = new LatLng(ocorrencia.getLatitude(), ocorrencia.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 16));
            mMap.addMarker(new MarkerOptions().position(coordenadas));
        } else {
            (findViewById(R.id.layoutMapaLocalizacaoDetalhes)).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void carregarObjetos() {
        txtTipoOcorrencia = findViewById(R.id.txt_detalhe_tipoOcorrencia);
        txtClassificacaoOcorrencia = findViewById(R.id.txt_detalhe_classificacaoOcorrencia);
        txtStatus = findViewById(R.id.txt_detalhe_status);
        txtDescricaoOcorrencia = findViewById(R.id.txt_detalhe_descricao);
        txtDescricaoLocalizacao = findViewById(R.id.txt_detalhe_desc_localizacao);
        txtInstituicaoAtendimento = findViewById(R.id.txt_instituicao_atendimento);
        txtDataAtendimento = findViewById(R.id.txt_dataAtendimento);
        txtMensagemAtendente = findViewById(R.id.txt_detalhe_mensagemAtendente);
    }

}
