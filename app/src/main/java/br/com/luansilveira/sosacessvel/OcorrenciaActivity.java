package br.com.luansilveira.sosacessvel;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import br.com.luansilveira.sosacessvel.Controller.ClassificacaoOcorrenciaController;
import br.com.luansilveira.sosacessvel.Controller.TipoOcorrenciaController;
import br.com.luansilveira.sosacessvel.Model.ClassificacaoOcorrencia;
import br.com.luansilveira.sosacessvel.Model.TipoOcorrencia;

public class OcorrenciaActivity extends AppCompatActivity {

    Spinner spClassifOcorrencias;
    Spinner spTipoOcorrencias;

    private ClassificacaoOcorrenciaController classificacaoController;
    private TipoOcorrenciaController tipoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        setContentView(R.layout.activity_ocorrencia);

        actionBar.setTitle("Nova Ocorrência");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        spClassifOcorrencias = findViewById(R.id.classifOcorrencias);
        spTipoOcorrencias = findViewById(R.id.tipoOcorrencias);

        classificacaoController = new ClassificacaoOcorrenciaController(getBaseContext());
        tipoController = new TipoOcorrenciaController(getBaseContext());

        Cursor cursorClassif = classificacaoController.retrieve();
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
}
