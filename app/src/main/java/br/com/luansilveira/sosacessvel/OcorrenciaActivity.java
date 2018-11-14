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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import br.com.luansilveira.sosacessvel.Controller.ClassificacaoOcorrenciaController;
import br.com.luansilveira.sosacessvel.Controller.OcorrenciaController;
import br.com.luansilveira.sosacessvel.Controller.OcorrenciaPreCadastradaController;
import br.com.luansilveira.sosacessvel.Controller.TipoOcorrenciaController;
import br.com.luansilveira.sosacessvel.Controller.UsuarioController;
import br.com.luansilveira.sosacessvel.FirebaseController.OcorrenciaFirebaseController;
import br.com.luansilveira.sosacessvel.Model.ClassificacaoOcorrencia;
import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.Model.OcorrenciaPreCadastrada;
import br.com.luansilveira.sosacessvel.Model.TipoOcorrencia;
import br.com.luansilveira.sosacessvel.Model.Usuario;
import br.com.luansilveira.sosacessvel.utils.Geolocalizacao;

public class OcorrenciaActivity extends AppCompatActivity implements OnMapReadyCallback {

    Spinner spClassifOcorrencias;
    Spinner spTipoOcorrencias;
    TextView txtLocalizacao;
    TextView txtInsituicao;
    EditText edDescricaoOcorrencia;
    EditText edDescricaoLocalizacao;

    private boolean cadastrarOcorrencia;
    private OcorrenciaPreCadastrada ocorrencia;
    private ClassificacaoOcorrenciaController classificacaoController;
    private TipoOcorrenciaController tipoController;
    private UsuarioController usuarioController;
    private OcorrenciaController ocorrenciaController;
    private OcorrenciaPreCadastradaController ocorrenciaPreCadastradaController;
    private OcorrenciaFirebaseController ocorrenciaFirebaseController;
    private TipoOcorrencia tipoOcorrenciaSelecionado;

    private GoogleMap map;
    private FusedLocationProviderClient fusedLocation;
    private Location local;

    private Geolocalizacao geolocalizacao;

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

        cadastrarOcorrencia = this.getIntent().getBooleanExtra("cadastrarOcorrencia", false);
        ocorrencia = (OcorrenciaPreCadastrada) this.getIntent().getSerializableExtra("ocorrencia");
        if(ocorrencia != null) cadastrarOcorrencia = true;

        spClassifOcorrencias = findViewById(R.id.classifOcorrencias);
        spTipoOcorrencias = findViewById(R.id.tipoOcorrencias);
        txtLocalizacao = findViewById(R.id.txtLocalizacao);
        txtInsituicao = findViewById(R.id.orgao_instituicao);
        edDescricaoOcorrencia = findViewById(R.id.edDescricaoOcorrencia);
        edDescricaoLocalizacao = findViewById(R.id.edDescricaoLocal);

        geolocalizacao = new Geolocalizacao(this);

        LinearLayout layoutLocalizacao = findViewById(R.id.layoutLocalizacao);
        Button btSolicitarAtendimento = findViewById(R.id.btSolicitarAtendimento);
        Button btCadastrarOcorrencia = findViewById(R.id.btCadastrarOcorrencia);

        if(ocorrencia != null) cadastrarOcorrencia = true;

        if(this.cadastrarOcorrencia){
            layoutLocalizacao.setVisibility(View.GONE);
            btSolicitarAtendimento.setVisibility(View.GONE);
        } else {
            btCadastrarOcorrencia.setVisibility(View.GONE);
        }

        try {
            classificacaoController = new ClassificacaoOcorrenciaController(this);
            tipoController = new TipoOcorrenciaController(this);
            usuarioController = new UsuarioController(this);
            ocorrenciaController = new OcorrenciaController(this);
            ocorrenciaPreCadastradaController = new OcorrenciaPreCadastradaController(this);
            ocorrenciaFirebaseController = new OcorrenciaFirebaseController();


            final List<ClassificacaoOcorrencia> listaClassificacoes = classificacaoController.queryForAll();
            this.popularSpinner(spClassifOcorrencias, listaClassificacoes);

            spClassifOcorrencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idClassificacao = ((ClassificacaoOcorrencia) parent.getItemAtPosition(position)).getId();
                    try {
                        List<TipoOcorrencia> listaTipos = tipoController.getTiposPelaClassificacao(idClassificacao);
                        popularSpinner(spTipoOcorrencias, listaTipos);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            spTipoOcorrencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tipoOcorrenciaSelecionado = (TipoOcorrencia) parent.getItemAtPosition(position);
                    txtInsituicao.setText(tipoOcorrenciaSelecionado.getNomeInstituicao());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!this.cadastrarOcorrencia && geolocalizacao.isPermissaoLocalizacao()) getLocalizacao();

        if(ocorrencia != null) this.popularCampos();

    }

    private boolean validarDescricaoOcorrencia(){
        boolean ok = true;

        if(tipoOcorrenciaSelecionado.getDescricao().equals("Outros")){
            ok = !(edDescricaoOcorrencia.getText().toString().trim().isEmpty());
            if (!ok){
                edDescricaoOcorrencia.setError(getString(R.string.erroDescricaoOcorrenciaObrigatoria));
                edDescricaoOcorrencia.requestFocus();
            }
        }

        return ok;
    }

    protected void popularSpinner(Spinner spinner, List<?> lista){

        ArrayAdapter<?> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista.toArray());

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


    protected void getLocalizacao(){

        geolocalizacao.setListener(new Geolocalizacao.GeolocalizacaoListener() {
            @Override
            public void onLocalizacaoEncontrada(Location local) {
                OcorrenciaActivity.this.local = local;
                txtLocalizacao.setText("Localização atual: " + String.valueOf(local.getLatitude())
                        + ", " + String.valueOf(local.getLongitude()));

                LatLng coordenadas = new LatLng(local.getLatitude(), local.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 15));
                map.addMarker(new MarkerOptions().position(coordenadas));
            }

            @Override
            public void onFalhaEncontrarLocalizacao() {
                (new AlertDialog.Builder(OcorrenciaActivity.this))
                        .setTitle("Atenção")
                        .setMessage("Não foi possível obter a localização atual. A localização não será enviada." +
                                "\nSendo assim, é de suma importância informar a descrição da localização, ou o endereço, para facilitar no atendimento.")
                        .setPositiveButton("OK", null).show();
            }
        }).getLocalizacao();
    }

    public void solicitarAtendimentoClick(View view) {
        if(!validarDescricaoOcorrencia()) return;

        try {
            Usuario usuario = usuarioController.getUsuario();
            String descricaoOcorrencia = edDescricaoOcorrencia.getText().toString();
            String descricaoLocalizacao = edDescricaoLocalizacao.getText().toString();

            Ocorrencia ocorrencia = new Ocorrencia(usuario, tipoOcorrenciaSelecionado, descricaoOcorrencia,
                    descricaoLocalizacao, local == null ? null : local.getLatitude(), local == null ? null : local.getLongitude());


            ocorrenciaController.create(ocorrencia);

            final Ocorrencia ocorr = ocorrencia;

            (new AlertDialog.Builder(this))
            .setTitle("Ocorrência solicitada")
                    .setMessage("A ocorrência foi enviada para central.\r\nAguarde o atendimento.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            abrirTelaDetalhesOcorrencia(ocorr);
                        }
                    }).create().show();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void abrirTelaDetalhesOcorrencia(Ocorrencia ocorrencia){
        Intent intent = new Intent(this, MapsDetalheOcorrenciaActivity.class)
                .putExtra("ocorrencia", ocorrencia);
        startActivity(intent);
        finish();
    }

    public void cadastrarOcorrenciaClick(View view) {
        if(!validarDescricaoOcorrencia()) return;

        try {
            Usuario usuario = usuarioController.getUsuario();
            String descricaoOcorrencia = edDescricaoOcorrencia.getText().toString();
            String descricaoLocalizacao = edDescricaoLocalizacao.getText().toString();

            OcorrenciaPreCadastrada ocorrencia = new OcorrenciaPreCadastrada(tipoOcorrenciaSelecionado, descricaoOcorrencia,
                    descricaoLocalizacao);

            if(this.ocorrencia != null){
                ocorrencia.setId(this.ocorrencia.getId());
                ocorrenciaPreCadastradaController.update(ocorrencia);

                Toast.makeText(this, "Ocorrência alterada", Toast.LENGTH_LONG).show();
            } else {
                ocorrenciaPreCadastradaController.create(ocorrencia);

                Toast.makeText(this, "Ocorrência cadastrada", Toast.LENGTH_LONG).show();
            }

            startActivity(new Intent(this, ListaOcorrenciasPreCadastradasActivity.class));
            finish();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void popularCampos(){
        edDescricaoOcorrencia.setText(ocorrencia.getDescricao());
        edDescricaoLocalizacao.setText(ocorrencia.getLocalizacao());
        tipoOcorrenciaSelecionado = ocorrencia.getTipoOcorrencia();
        this.selecionarCampo(spClassifOcorrencias, ocorrencia.getTipoOcorrencia().getClassificacaoOcorrencia());
        this.selecionarCampo(spTipoOcorrencias, tipoOcorrenciaSelecionado);
    }

    private void selecionarCampo(Spinner spinner, Object data){

        try {
            for (int id = 0; id < spinner.getCount(); id++) {
                Object obj = spinner.getItemAtPosition(id);
                if ((int) obj.getClass().getMethod("getId").invoke(obj) == (int) data.getClass().getMethod("getId").invoke(data)) {
                    spinner.setSelection(id);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                geolocalizacao.getLocalizacao();
                return true;
            }
        });
    }
}
