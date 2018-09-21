package br.com.luansilveira.sosacessvel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.luansilveira.sosacessvel.Controller.UsuarioController;
import br.com.luansilveira.sosacessvel.Enum.Rh;
import br.com.luansilveira.sosacessvel.Enum.TipoSanguineo;
import br.com.luansilveira.sosacessvel.Model.Usuario;
import br.com.luansilveira.sosacessvel.utils.DB;

public class CadastroUsuarioActivity extends AppCompatActivity {
    protected UsuarioController crud;
    protected boolean editarUsuario;

    EditText edNome;
    EditText edDataNascimento;
    RadioGroup rgTipoSanguineo;
    RadioGroup rgRhSanguineo;
    EditText edEndereco;
    EditText edInfMedicas;
    RadioButton rbTipo;
    RadioButton rbRh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cadastro_usuario);

        edNome = findViewById(R.id.edNome);
        edDataNascimento = findViewById(R.id.edDataNasc);
        rgTipoSanguineo = findViewById(R.id.rgTipoSanguineo);
        rgRhSanguineo =  findViewById(R.id.rgRHSanguineo);
        edEndereco = findViewById(R.id.edEndereco);
        edInfMedicas = findViewById(R.id.edInfMedicas);

        this.editarUsuario = getIntent().getBooleanExtra("editar_usuario", false);

        crud = new UsuarioController(getBaseContext());

        if (this.editarUsuario){
            this.carregarUsuario();
        } else {
            if (crud.existeUsuario()) {
                abrirTelaInicial();
            }
        }
    }

    public void salvarClick(View view) {
        Usuario usuario = this.popularUsuario();

        if (this.editarUsuario) {
            long retorno = crud.update(usuario);
            if(retorno == -1){
                Toast.makeText(this, "Erro ao gravar os dados!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Usuário alterado!", Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            long retorno = crud.create(usuario);
            if (retorno == -1) {
                Toast.makeText(this, "Erro ao gravar os dados!", Toast.LENGTH_LONG).show();
            } else {
                try {
                    (new DB(getBaseContext())).execSQLFromFile(R.raw.sos_acessivel_sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                abrirTelaInicial();
            }
        }
    }

    public void abrirTelaInicial(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public Usuario popularUsuario(){

        rbTipo = findViewById(rgTipoSanguineo.getCheckedRadioButtonId());
        rbRh = findViewById(rgRhSanguineo.getCheckedRadioButtonId());

        String tipo = TipoSanguineo.values()[rgTipoSanguineo.indexOfChild(rbTipo)].toString();
        String rh = Rh.values()[rgRhSanguineo.indexOfChild(rbRh)].toString();

        Usuario usuario = null;
        try {
            usuario = new Usuario(
                    1, edNome.getText().toString(), (new SimpleDateFormat("dd/MM/yyyy")).parse(edDataNascimento.getText().toString()),
                    TipoSanguineo.valueOf(tipo), Rh.valueOf(rh), edEndereco.getText().toString(), edInfMedicas.getText().toString()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public void carregarUsuario(){
        Usuario usuario = crud.getUsuario();
        edNome.setText(usuario.getNome());
        edEndereco.setText(usuario.getEndereco());
        edDataNascimento.setText(usuario.getDataNascimentoString());
        edInfMedicas.setText(usuario.getInfMedicas());

        rgTipoSanguineo.clearCheck();
        rgRhSanguineo.clearCheck();

        ((RadioButton) rgTipoSanguineo.getChildAt(usuario.getTipoSanguineo().ordinal())).setChecked(true);
        ((RadioButton) rgRhSanguineo.getChildAt(usuario.getRhSanguineo().ordinal())).setChecked(true);

    }
}
