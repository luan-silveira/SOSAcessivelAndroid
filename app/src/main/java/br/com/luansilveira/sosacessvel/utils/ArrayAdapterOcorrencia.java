package br.com.luansilveira.sosacessvel.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.R;

public class ArrayAdapterOcorrencia extends ArrayAdapter<Ocorrencia> {

    private Context context;
    private ArrayList<Ocorrencia> lista;

    public ArrayAdapterOcorrencia(Context context, ArrayList<Ocorrencia> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Ocorrencia ocorrencia = lista.get(position);
        int colorShape = Color.RED;
        String status = "Status: ";

        convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_ocorrencia, null);
        TextView textoTipoOcorrencia = (TextView) convertView.findViewById(R.id.texto_tipo_ocorrencia);
        TextView textoStatus = (TextView) convertView.findViewById(R.id.texto_status_ocorrencia);
        TextView textoDataHora = (TextView) convertView.findViewById(R.id.texto_data_hora);
        ImageView shape = convertView.findViewById(R.id.imageView);

        String txtData = "Data/Hora: " + ocorrencia.getDataOcorrencia();
        textoTipoOcorrencia.setText(ocorrencia.toString());
        textoDataHora.setText(txtData);

        switch(ocorrencia.getStatus()){
            case 1:
                colorShape = Color.RED;
                status += "Aguardando atendimento";
                break;
            case 2:
                colorShape = Color.YELLOW;
                status += "Atendida";
                break;
            case 3:
                colorShape = Color.BLUE;
                status += "Socorro enviado";
                break;
            case 4:
                colorShape = Color.GREEN;
                status += "Finalizada";
                break;
        }

        textoStatus.setText(status);
        setColorShape(shape, colorShape);

        return convertView;
    }


    private void setColorShape(ImageView shape, int color){
        Drawable background = shape.getDrawable();
        ((GradientDrawable) background).setColor(color);
    }
}
