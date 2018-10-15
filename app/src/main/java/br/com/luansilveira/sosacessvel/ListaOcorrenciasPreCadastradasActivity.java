package br.com.luansilveira.sosacessvel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.luansilveira.sosacessvel.Controller.OcorrenciaPreCadastradaController;
import br.com.luansilveira.sosacessvel.Model.OcorrenciaPreCadastrada;
import br.com.luansilveira.sosacessvel.utils.ArrayAdapterOcorrenciaPre;

public class ListaOcorrenciasPreCadastradasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ocorrencias_pre_cadastradas);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Ocorrências pré-cadastradas");
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setHomeButtonEnabled(true);



        try {
            ArrayList<OcorrenciaPreCadastrada> listaOcorrencias =(ArrayList<OcorrenciaPreCadastrada>) (new OcorrenciaPreCadastradaController(this)).getListaOcorrencias();
            ArrayAdapterOcorrenciaPre adapter = new ArrayAdapterOcorrenciaPre(this, listaOcorrencias);

            ListView listViewOcorencias = findViewById(R.id.listaOcorrenciasPreCadastradas);
            listViewOcorencias.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ocorrenciaPreCadastradaClick(View view) {
        Intent intent = (new Intent(this, OcorrenciaActivity.class))
                .putExtra("cadastrarOcorrencia", true);
        startActivity(intent);
        finish();
    }
}
