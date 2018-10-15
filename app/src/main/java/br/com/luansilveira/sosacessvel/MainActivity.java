package br.com.luansilveira.sosacessvel;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.luansilveira.sosacessvel.Controller.AtendenteController;
import br.com.luansilveira.sosacessvel.Controller.OcorrenciaController;
import br.com.luansilveira.sosacessvel.Controller.OcorrenciaPreCadastradaController;
import br.com.luansilveira.sosacessvel.Controller.UsuarioController;
import br.com.luansilveira.sosacessvel.Model.Atendente;
import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.Model.OcorrenciaPreCadastrada;
import br.com.luansilveira.sosacessvel.utils.ArrayAdapterOcorrenciaPre;
import br.com.luansilveira.sosacessvel.utils.Geolocalizacao;
import br.com.luansilveira.sosacessvel.utils.Notify;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected UsuarioController userCtrl;
    protected OcorrenciaController ocorrenciaCtrl;
    protected AtendenteController atendenteCtrl;
    protected ArrayList<Ocorrencia> listaOcorrencias = new ArrayList<>();
    protected ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SOS Acessível");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);   

        this.solicitarPermissoes();

        try {
            userCtrl = new UsuarioController(this);
            ocorrenciaCtrl = new OcorrenciaController(this);
            atendenteCtrl = new AtendenteController(this);

            FirebaseDatabase.getInstance().getReference("ocorrencias").orderByChild("usuario/key").equalTo(userCtrl.getUsuario().getKey())
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {}

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            try {
                                Ocorrencia ocorrencia = dataSnapshot.getValue(Ocorrencia.class);
                                Atendente atendente = ocorrencia.getAtendente();

                                if(atendente != null){
                                    atendenteCtrl.createIfNotExists(atendente);
                                }
                                int retorno = ocorrenciaCtrl.update(ocorrencia);

                                if (retorno == 1) {
                                    Toast.makeText(MainActivity.this, "Ocorrencia "+ ocorrencia.getId()
                                            + " alterada.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, MapsDetalheOcorrenciaActivity.class);
                                    intent.putExtra("ocorrencia", ocorrencia);
                                    Notify.criarNotificacao(MainActivity.this, intent, R.drawable.ic_notificacao_emergencia,
                                            "Ocorrência atendida",
                                            "A ocorrência foi atendida.");
                                }
                            } catch (SQLException e) {
                                Toast.makeText(MainActivity.this, "Erro de sincronização", Toast.LENGTH_LONG).show();
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {}

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.menu_ocorrencias) {
            startActivity(new Intent(MainActivity.this, ListaOcorrenciasActivity.class));
        }

        if (id == R.id.menuCadastro){

            Intent intent = new Intent(this, CadastroUsuarioActivity.class);
            intent.putExtra("editar_usuario", true);
            startActivity(intent);
        }

        if (id == R.id.menu_ocorrencia_predef){
            startActivity(new Intent(this, ListaOcorrenciasPreCadastradasActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void novaOcorrenciaClick(View view) {
        startActivity(new Intent(this, OcorrenciaActivity.class));
    }

    public void solicitarPermissoes(){
        ArrayList<String> permissoesRequisitar = new ArrayList<>();
        String[] permissoesRequisitarArray = new String[]{};

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permissoesRequisitar.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permissoesRequisitar.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!permissoesRequisitar.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissoesRequisitar.toArray(permissoesRequisitarArray), 1);
        }
    }

    public void ocorrenciaPreCadastradaClick(View view) {
        try {
            ArrayList<OcorrenciaPreCadastrada> listaOcorrencias = (ArrayList<OcorrenciaPreCadastrada>) (new OcorrenciaPreCadastradaController(this)).getListaOcorrencias();
            final ArrayAdapterOcorrenciaPre adapter = new ArrayAdapterOcorrenciaPre(this, listaOcorrencias);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            if(listaOcorrencias.size() > 0) {
                dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final OcorrenciaPreCadastrada ocorrencia = adapter.getItem(which);

                        AlertDialog.Builder dialogIn = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Enviar ocorrência")
                                .setMessage("Deseja realmente enviar esta ocorrência?");
                        dialogIn.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialogIn.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Geolocalizacao geo = new Geolocalizacao(MainActivity.this)
                                        .setListener(new Geolocalizacao.GeolocalizacaoListener() {
                                            @Override
                                            public void onLocalizacaoEncontrada(Location local) {
                                                enviarOcorrenciaPreCadastrada(ocorrencia, local);
                                            }
                                        });
                                geo.getLocalizacao();
                            }
                        });
                        dialogIn.show();
                    }
                });
            } else {
                dialog.setMessage("Não há ocorrências pré-cadastradas");
            }
            dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            dialog.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enviarOcorrenciaPreCadastrada(OcorrenciaPreCadastrada ocorrenciaPreCadastrada, Location local){
        try {
            Ocorrencia ocorrencia = new Ocorrencia(
                    userCtrl.getUsuario(),
                    ocorrenciaPreCadastrada.getTipoOcorrencia(),
                    ocorrenciaPreCadastrada.getDescricao(),
                    ocorrenciaPreCadastrada.getLocalizacao(),
                    local.getLatitude(),
                    local.getLongitude());

            ocorrenciaCtrl.create(ocorrencia);

            Toast.makeText(this, "Ocorrência enviada!", Toast.LENGTH_LONG).show();

            startActivity((new Intent(this, MapsDetalheOcorrenciaActivity.class)).putExtra("ocorrencia", ocorrencia));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
