package br.com.luansilveira.sosacessvel;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class OcorrenciaActivity extends AppCompatActivity {

    Spinner spClassifOcorrencias;
    Spinner spTipoOcorrencias;

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

        ArrayAdapter<CharSequence> adapterClassif = ArrayAdapter.createFromResource(this, R.array.classificacoes_ocorrencias,
                R.layout.support_simple_spinner_dropdown_item);
        adapterClassif.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClassifOcorrencias.setAdapter(adapterClassif);
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
