package br.com.luansilveira.sosacessvel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.luansilveira.sosacessvel.Controller.OcorrenciaPreCadastradaController;
import br.com.luansilveira.sosacessvel.Model.OcorrenciaPreCadastrada;
import br.com.luansilveira.sosacessvel.utils.ArrayAdapterOcorrenciaPre;

public class ListaOcorrenciasPreCadastradasActivity extends AppCompatActivity {

    TextView txtVazio;
    private ArrayList<OcorrenciaPreCadastrada> listaOcorrencias;
    private ArrayAdapterOcorrenciaPre adapter;

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
            listaOcorrencias = (ArrayList<OcorrenciaPreCadastrada>) (new OcorrenciaPreCadastradaController(this)).getListaOcorrencias();
            adapter = new ArrayAdapterOcorrenciaPre(this, listaOcorrencias);

            txtVazio.setVisibility(listaOcorrencias.size() == 0 ? View.VISIBLE : View.GONE);

            ListView listViewOcorencias = findViewById(R.id.listaOcorrenciasPreCadastradas);
            listViewOcorencias.setOnItemLongClickListener((parent, view, position, id) -> {
                final OcorrenciaPreCadastrada ocorrenciaPreCadastrada = (OcorrenciaPreCadastrada) parent.getItemAtPosition(position);
                PopupMenu popupMenu = new PopupMenu(ListaOcorrenciasPreCadastradasActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_ocorrencia_pre, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.ocorrencia_editar) {
                        startActivity((new Intent(ListaOcorrenciasPreCadastradasActivity.this, OcorrenciaActivity.class))
                                .putExtra("ocorrencia", ocorrenciaPreCadastrada));
                        finish();
                    }

                    if (item.getItemId() == R.id.ocorrencia_excluir) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ListaOcorrenciasPreCadastradasActivity.this)
                                .setCancelable(false)
                                .setTitle("Excluir ocorrência")
                                .setMessage("Deseja reamente excluir a ocorrência?");
                        dialog.setNegativeButton("Não", (dialog1, which) -> dialog1.dismiss());
                        dialog.setPositiveButton("Sim", (dialog12, which) -> excluirOcorrencia(ocorrenciaPreCadastrada));
                        dialog.show();
                    }

                    return true;
                });
                popupMenu.show();
                return true;
            });
            listViewOcorencias.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirOcorrencia(OcorrenciaPreCadastrada ocorrencia) {
        try {
            OcorrenciaPreCadastradaController controller = new OcorrenciaPreCadastradaController(this);
            if (controller.delete(ocorrencia) == 1) {
                listaOcorrencias.remove(ocorrencia);
                adapter.notifyDataSetChanged();
                if (listaOcorrencias.size() == 0) txtVazio.setVisibility(View.VISIBLE);
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
