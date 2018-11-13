package br.com.luansilveira.sosacessvel;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.luansilveira.sosacessvel.Controller.OcorrenciaController;
import br.com.luansilveira.sosacessvel.Controller.OcorrenciaPreCadastradaController;
import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.Model.OcorrenciaPreCadastrada;
import br.com.luansilveira.sosacessvel.utils.ArrayAdapterOcorrenciaPre;

public class ListaOcorrenciasPreCadastradasActivity extends AppCompatActivity {

    private ArrayList<OcorrenciaPreCadastrada> listaOcorrencias;
    private ArrayAdapterOcorrenciaPre adapter;
    TextView txtVazio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ocorrencias_pre_cadastradas);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Ocorrências pré-cadastradas");
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setHomeButtonEnabled(true);

        txtVazio = findViewById(R.id.txt_sem_ocorrencias_pre);

        try {
            listaOcorrencias =(ArrayList<OcorrenciaPreCadastrada>) (new OcorrenciaPreCadastradaController(this)).getListaOcorrencias();
            adapter = new ArrayAdapterOcorrenciaPre(this, listaOcorrencias);

            txtVazio.setVisibility(listaOcorrencias.size() == 0 ? View.VISIBLE : View.GONE);

            ListView listViewOcorencias = findViewById(R.id.listaOcorrenciasPreCadastradas);
            listViewOcorencias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final OcorrenciaPreCadastrada ocorrenciaPreCadastrada = (OcorrenciaPreCadastrada) parent.getItemAtPosition(position);
                    PopupMenu popupMenu = new PopupMenu(ListaOcorrenciasPreCadastradasActivity.this, view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_ocorrencia_pre, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId() == R.id.ocorrencia_editar){
                                startActivity((new Intent(ListaOcorrenciasPreCadastradasActivity.this, OcorrenciaActivity.class))
                                .putExtra("ocorrencia", ocorrenciaPreCadastrada));
                                finish();
                            }

                            if(item.getItemId() == R.id.ocorrencia_excluir){
                                AlertDialog.Builder dialog = new AlertDialog.Builder(ListaOcorrenciasPreCadastradasActivity.this)
                                        .setTitle("Excluir ocorrência")
                                        .setMessage("Deseja reamente excluir a ocorrência?");
                                dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                                });
                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        excluirOcorrencia(ocorrenciaPreCadastrada);
                                    }
                                });
                                dialog.show();
                            }

                            return true;
                        }
                    });
                    popupMenu.show();
                    return true;
                }
            });
            listViewOcorencias.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirOcorrencia(OcorrenciaPreCadastrada ocorrencia){
        try {
            OcorrenciaPreCadastradaController controller = new OcorrenciaPreCadastradaController(this);
            if (controller.delete(ocorrencia) == 1){
                listaOcorrencias.remove(ocorrencia);
                adapter.notifyDataSetChanged();
                if(listaOcorrencias.size() == 0) txtVazio.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Ocorrência removida", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Erro ao excluir", Toast.LENGTH_LONG).show();
            }
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
