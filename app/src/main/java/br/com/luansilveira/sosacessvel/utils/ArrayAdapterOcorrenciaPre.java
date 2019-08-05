package br.com.luansilveira.sosacessvel.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.luansilveira.sosacessvel.Model.OcorrenciaPreCadastrada;
import br.com.luansilveira.sosacessvel.R;

public class ArrayAdapterOcorrenciaPre extends ArrayAdapter<OcorrenciaPreCadastrada> {

    private Context context;
    private List<OcorrenciaPreCadastrada> lista;

    public ArrayAdapterOcorrenciaPre(Context context, ArrayList<OcorrenciaPreCadastrada> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OcorrenciaPreCadastrada ocorrencia = lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_ocorrencia_pre, null);
        TextView txtDescricao = convertView.findViewById(R.id.txtDescricaoOcorrencia);
        TextView txtTipo = convertView.findViewById(R.id.txtTipoOcorrencia);

        txtDescricao.setText(ocorrencia.getId() + ". " +
                ocorrencia.getDescricao() == null || ocorrencia.getDescricao().trim().isEmpty() ?
                "<sem descrição>" : ocorrencia.getDescricao());
        txtTipo.setText(ocorrencia.getTipoOcorrencia().getClassificacaoOcorrencia().getId()
                + "." + ocorrencia.getTipoOcorrencia().getId() + " - " + ocorrencia.getTipoOcorrencia().toString());

        return convertView;
    }
}
