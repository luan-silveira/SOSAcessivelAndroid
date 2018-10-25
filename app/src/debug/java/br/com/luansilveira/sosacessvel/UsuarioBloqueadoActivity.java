package br.com.luansilveira.sosacessvel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UsuarioBloqueadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_bloqueado);
        getSupportActionBar().hide();
    }
}
