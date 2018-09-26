package br.com.luansilveira.sosacessvel.FirebaseController;

import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.OcorrenciaActivity;

public class OcorrenciaFirebaseController {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public OcorrenciaFirebaseController() {

    }

    public void create(final Ocorrencia ocorrencia){
        try {
            DatabaseReference dbRef = database.getReference("ocorrencias").push();
            ocorrencia.setKey(dbRef.getKey());
            dbRef.setValue(ocorrencia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(final Ocorrencia ocorrencia){
        DatabaseReference dbRef = database.getReference("ocorrencias").child(ocorrencia.getKey());
            dbRef.setValue(ocorrencia);
    }

    public void delete(final Ocorrencia ocorrencia){
        DatabaseReference dbRef = database.getReference("ocorrencias").child(ocorrencia.getKey());
        dbRef.removeValue();
    }

}
