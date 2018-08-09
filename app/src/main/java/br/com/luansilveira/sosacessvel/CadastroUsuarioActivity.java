package br.com.luansilveira.sosacessvel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

import br.com.luansilveira.sosacessvel.Controller.UsuarioController;
import br.com.luansilveira.sosacessvel.Model.Usuario;
import br.com.luansilveira.sosacessvel.utils.DB;

public class CadastroUsuarioActivity extends AppCompatActivity {
    protected UsuarioController crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cadastro_usuario);

        crud = new UsuarioController(getBaseContext());

        if(crud.existeUsuario()){
            abrirTelaInicial();
        }
    }

    public void salvarClick(View view) {
        long retorno = crud.create(this.popularUsuario());
        if(retorno == -1){
            Toast.makeText(this, "Erro ao gravar os dados!", Toast.LENGTH_LONG).show();
        } else {
            try {
                (new DB(getBaseContext())).execSQLFromFile(R.raw.sos_acessivel_sql);
            } catch (IOException e) {
                e.printStackTrace();
            }

            abrirTelaInicial();
        }
    }

    public void abrirTelaInicial(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public Usuario popularUsuario(){
        EditText edNome = findViewById(R.id.edNome);
        EditText edDataNascimento = findViewById(R.id.edDataNasc);
        RadioGroup rgTipoSanguineo = findViewById(R.id.rgTipoSanguineo);
        RadioGroup rgRhSanguineo =  findViewById(R.id.rgRHSanguineo);
        EditText edEndereco = findViewById(R.id.edEndereco);
        EditText edInfMedicas = findViewById(R.id.edInfMedicas);

        RadioButton rbTipo = findViewById(rgTipoSanguineo.getCheckedRadioButtonId());
        String[] valores = new String[]{"A", "B", "AB", "O"};
        String tipo = valores[rgTipoSanguineo.indexOfChild(rbTipo)];

        RadioButton rbRh = findViewById(rgRhSanguineo.getCheckedRadioButtonId());
        valores = new String[]{"P", "N"};
        String rh = valores[rgRhSanguineo.indexOfChild(rbRh)];

        Usuario usuario = new Usuario(
                1, edNome.getText().toString(), edDataNascimento.getText().toString(),
                tipo, rh, edEndereco.getText().toString(), edInfMedicas.getText().toString()
        );

        return usuario;
    }
}
