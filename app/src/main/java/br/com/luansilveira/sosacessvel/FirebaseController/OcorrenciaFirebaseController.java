package br.com.luansilveira.sosacessvel.FirebaseController;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.luansilveira.sosacessvel.Model.Ocorrencia;

public class OcorrenciaFirebaseController {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public OcorrenciaFirebaseController() {

    }

    public void create(final Ocorrencia ocorrencia) {
        try {
            DatabaseReference dbRef = database.getReference("ocorrencias").push();
            ocorrencia.setKey(dbRef.getKey());
            dbRef.setValue(ocorrencia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(final Ocorrencia ocorrencia) {
        DatabaseReference dbRef = database.getReference("ocorrencias").child(ocorrencia.getKey());
        dbRef.setValue(ocorrencia);
    }

    public void delete(final Ocorrencia ocorrencia) {
        DatabaseReference dbRef = database.getReference("ocorrencias").child(ocorrencia.getKey());
        dbRef.removeValue();
    }

}
