package br.com.luansilveira.sosacessvel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class UsuarioBloqueadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_bloqueado);
        getSupportActionBar().hide();
    }
}
