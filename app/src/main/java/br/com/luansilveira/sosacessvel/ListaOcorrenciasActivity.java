package br.com.luansilveira.sosacessvel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.luansilveira.sosacessvel.Controller.OcorrenciaController;
import br.com.luansilveira.sosacessvel.Controller.UsuarioController;
import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.utils.ArrayAdapterOcorrencia;

public class ListaOcorrenciasActivity extends AppCompatActivity {

    protected UsuarioController userCtrl;
    protected ArrayList<Ocorrencia> listaOcorrencias = new ArrayList<>();
    protected ListView listView;
    protected Spinner spinner;
    protected OcorrenciaController ocorrenciaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ocorrencias);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Lista de Ocorrências");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        listView = findViewById(R.id.listaOcorrencias);
        spinner = findViewById(R.id.spinner_tipo_ocorrencias);

        try {
            ocorrenciaController = new OcorrenciaController(this);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        try {
                            listaOcorrencias = (ArrayList<Ocorrencia>) ocorrenciaController.getListaOcorrencias();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            listaOcorrencias = (ArrayList<Ocorrencia>) ocorrenciaController.getListaOcorrenciasPorStatus(position - 1);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    popularListView();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            this.popularListView();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Ocorrencia ocorrencia = (Ocorrencia) parent.getItemAtPosition(position);
                    Intent intent = new Intent(ListaOcorrenciasActivity.this, MapsDetalheOcorrenciaActivity.class);
                    intent.putExtra("ocorrencia", ocorrencia);
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    public void popularListView(){
        ArrayAdapterOcorrencia adapterOcorrencia = new ArrayAdapterOcorrencia(ListaOcorrenciasActivity.this, listaOcorrencias);
        listView.setAdapter(adapterOcorrencia);
    }

}
