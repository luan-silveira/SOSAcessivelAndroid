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

        final int RED = Color.rgb(175,0,0);
        final int GREEN = Color.rgb(0,132,0);
        final int BLUE = Color.rgb(0,0,142);

        Ocorrencia ocorrencia = lista.get(position);
        int colorShape = RED;
        String status = "Status: ";

        convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_ocorrencia, null);
        TextView textoTipoOcorrencia = (TextView) convertView.findViewById(R.id.texto_tipo_ocorrencia);
        TextView textoStatus = (TextView) convertView.findViewById(R.id.texto_status_ocorrencia);
        TextView textoDataHora = (TextView) convertView.findViewById(R.id.texto_data_hora);
        ImageView shape = convertView.findViewById(R.id.imageView);

        String txtData = "Data/Hora: " + ocorrencia.getDataOcorrencia();
        textoTipoOcorrencia.setText(ocorrencia.getDescricaoTipoOcorrencia());
        textoDataHora.setText(txtData);

        switch(ocorrencia.getStatus()){
            case 0:
                colorShape = RED;
                break;
            case 1:
                colorShape = BLUE;
                break;
            case 2:
                colorShape = GREEN;
                break;
        }

        textoStatus.setText("Status: " + ocorrencia.getDescricaoStatus());
        setColorShape(shape, colorShape);

        return convertView;
    }


    private void setColorShape(ImageView shape, int color){
        Drawable background = shape.getDrawable();
        ((GradientDrawable) background).setColor(color);
    }
}
