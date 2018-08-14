package br.com.luansilveira.sosacessvel.FirebaseController;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.luansilveira.sosacessvel.Model.Ocorrencia;

public class OcorrenciaController {

    private FirebaseDatabase database;

    public OcorrenciaController() {
        database = FirebaseDatabase.getInstance();
    }

    public void create(final Ocorrencia ocorrencia){
        DatabaseReference dbRef = database.getReference("ocorrencias").push();
        ocorrencia.setKey(dbRef.getKey());
        dbRef.setValue(ocorrencia);
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
