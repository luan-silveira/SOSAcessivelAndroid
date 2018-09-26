package br.com.luansilveira.sosacessvel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.luansilveira.sosacessvel.Controller.UsuarioController;
import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.utils.ArrayAdapterOcorrencia;

public class ListaOcorrenciasActivity extends AppCompatActivity {

    protected UsuarioController userCtrl;
    protected ArrayList<Ocorrencia> listaOcorrencias = new ArrayList<>();
    protected ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ocorrencias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SOS Acessível");
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listaOcorrencias);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("ocorrencias");
        dbRef.orderByChild("usuario/key").equalTo((new UsuarioController(getBaseContext())).getUsuario().getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaOcorrencias.clear();
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    Ocorrencia ocorrencia = item.getValue(Ocorrencia.class);
                    listaOcorrencias.add(ocorrencia);
                }
                popularListView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ocorrencia ocorrencia = (Ocorrencia) parent.getAdapter().getItem(position);
                Intent intent = new Intent(ListaOcorrenciasActivity.this, MapsDetalheOcorrenciaActivity.class);
                intent.putExtra("ocorrencia", ocorrencia);
                startActivity(intent);
            }
        });
    }

    public void popularListView(){
        ArrayAdapterOcorrencia adapterOcorrencia = new ArrayAdapterOcorrencia(ListaOcorrenciasActivity.this, listaOcorrencias);
        listView.setAdapter(adapterOcorrencia);
    }

}
