package br.com.luansilveira.sosacessvel.FirebaseController;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.luansilveira.sosacessvel.Model.Usuario;

public abstract class UserFirebaseController {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();


    public static String save(final Usuario usuario){
        String key = new String();
        try {
            DatabaseReference dbRef = database.getReference("usuarios").push();
            key = dbRef.getKey();
            usuario.setKey(key);
            dbRef.setValue(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }

    public static void update(final Usuario usuario){
        DatabaseReference dbRef = database.getReference("usuarios").child(usuario.getKey());
        dbRef.setValue(usuario);
    }

    public static void delete(final Usuario usuario){
        DatabaseReference dbRef = database.getReference("usuarios").child(usuario.getKey());
        dbRef.removeValue();
    }
}

