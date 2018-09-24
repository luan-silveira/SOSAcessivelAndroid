package br.com.luansilveira.sosacessvel;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import br.com.luansilveira.sosacessvel.Controller.ClassificacaoOcorrenciaController;
import br.com.luansilveira.sosacessvel.Controller.TipoOcorrenciaController;
import br.com.luansilveira.sosacessvel.Controller.UsuarioController;
import br.com.luansilveira.sosacessvel.FirebaseController.OcorrenciaController;
import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.Model.TipoOcorrencia;
import br.com.luansilveira.sosacessvel.Model.Usuario;
import br.com.luansilveira.sosacessvel.utils.OcorrenciaListener;

public class OcorrenciaActivity extends AppCompatActivity implements OnMapReadyCallback {

    Spinner spClassifOcorrencias;
    Spinner spTipoOcorrencias;
    TextView txtLocalizacao;
    EditText edDescricaoOcorrencia;
    EditText edDescricaoLocalizacao;

    private ClassificacaoOcorrenciaController classificacaoController;
    private TipoOcorrenciaController tipoController;
    private UsuarioController usuarioController;
    private OcorrenciaController ocorrenciaController;
    private TipoOcorrencia tipoOcorrenciaSelecionado;

    private GoogleMap map;
    private FusedLocationProviderClient fusedLocation;
    private Location local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_ocorrencia);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa_localizacao);
        mapFragment.getMapAsync(this);

        actionBar.setTitle("Nova Ocorrência");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        spClassifOcorrencias = findViewById(R.id.classifOcorrencias);
        spTipoOcorrencias = findViewById(R.id.tipoOcorrencias);
        txtLocalizacao = findViewById(R.id.txtLocalizacao);
        edDescricaoOcorrencia = findViewById(R.id.edDescricaoOcorrencia);
        edDescricaoLocalizacao = findViewById(R.id.edDescricaoLocal);

        classificacaoController = new ClassificacaoOcorrenciaController(getBaseContext());
        tipoController = new TipoOcorrenciaController(getBaseContext());
        usuarioController = new UsuarioController(getBaseContext());
        ocorrenciaController = new OcorrenciaController();

        final Cursor cursorClassif = classificacaoController.retrieve();
        this.popularSpinner(spClassifOcorrencias, cursorClassif, new String[]{"descricao", "_id"});

        spClassifOcorrencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursorClassif = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
                int idClassificacao = cursorClassif.getInt(cursorClassif.getColumnIndexOrThrow("_id"));
                Cursor cursorTipo = tipoController.retrieve(idClassificacao);
                popularSpinner(spTipoOcorrencias, cursorTipo, new String[]{"descricao"});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTipoOcorrencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursorTipo = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
                tipoOcorrenciaSelecionado = tipoController.popularTipoOcorrencia(cursorTipo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(isPermissaoLocalizacao()) getLocalizacao();

    }

    protected void popularSpinner(Spinner spinner, Cursor cursor, String[] campos){
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,
                cursor, campos, new int[]{android.R.id.text1});

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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

    protected boolean isPermissaoLocalizacao(){
        return ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED));
    }

    protected void getLocalizacao(){
        fusedLocation = LocationServices.getFusedLocationProviderClient(this);
        try {
            if(this.isPermissaoLocalizacao()){
                final Task localizacao = fusedLocation.getLastLocation();
                localizacao.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            local = (Location) task.getResult();
                            txtLocalizacao.setText("Localização atual: " + String.valueOf(local.getLatitude())
                                + ", " + String.valueOf(local.getLongitude()));

                            LatLng coordenadas = new LatLng(local.getLatitude(), local.getLongitude());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 15));
                            map.addMarker(new MarkerOptions().position(coordenadas));
                        }
                    }
                });
            }

        } catch (SecurityException e){
            Log.e("Exception", e.getMessage());
        }
    }

    public void solicitarAtendimentoClick(View view) {

        Usuario usuario = usuarioController.getUsuario();
        String descricaoOcorrencia = edDescricaoOcorrencia.getText().toString();
        String descricaoLocalizacao = edDescricaoLocalizacao.getText().toString();

        Ocorrencia ocorrencia = new Ocorrencia(usuario, tipoOcorrenciaSelecionado, descricaoOcorrencia,
                descricaoLocalizacao, local.getLatitude(), local.getLongitude());


        ocorrenciaController.create(ocorrencia);
        AlertDialog.Builder  dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Ocorrência solicitada")
            .setMessage("A ocorrência foi enviada para central.\r\nAguarde o atendimento.")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(OcorrenciaActivity.this, MapsDetalheOcorrenciaActivity.class));
                    finish();
                }
            }).create().show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
    }
}
