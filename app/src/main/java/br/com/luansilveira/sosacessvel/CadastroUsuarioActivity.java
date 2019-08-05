package br.com.luansilveira.sosacessvel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.luansilveira.sosacessvel.Controller.UsuarioController;
import br.com.luansilveira.sosacessvel.Enum.Rh;
import br.com.luansilveira.sosacessvel.Enum.TipoSanguineo;
import br.com.luansilveira.sosacessvel.FirebaseController.UserFirebaseController;
import br.com.luansilveira.sosacessvel.Model.Usuario;
import br.com.luansilveira.sosacessvel.utils.DB;
import br.com.luansilveira.sosacessvel.utils.MaskEditUtil;

public class CadastroUsuarioActivity extends AppCompatActivity {
    protected UsuarioController crud;
    protected boolean editarUsuario;

    protected String key;

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
        rgRhSanguineo = findViewById(R.id.rgRHSanguineo);
        edEndereco = findViewById(R.id.edEndereco);
        edInfMedicas = findViewById(R.id.edInfMedicas);

        ((RadioButton) findViewById(R.id.rbTipoA)).setChecked(true);
        ((RadioButton) findViewById(R.id.rbTipoPositivo)).setChecked(true);

        edDataNascimento.addTextChangedListener(MaskEditUtil.mask(edDataNascimento, MaskEditUtil.FORMAT_DATE));

        this.editarUsuario = getIntent().getBooleanExtra("editar_usuario", false);

        try {
            crud = new UsuarioController(getBaseContext());

            if (this.editarUsuario) {
                this.carregarUsuario();
            } else {
                if (crud.existeUsuario()) {
                    abrirTelaInicial();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void salvarClick(View view) {
        if (!validarCampos()) return;

        Usuario usuario = this.popularUsuario();

        try {
            if (this.editarUsuario) {
                usuario.setKey(this.key);
                long retorno = crud.update(usuario);
                if (retorno == -1) {
                    Toast.makeText(this, "Erro ao gravar os dados!", Toast.LENGTH_LONG).show();
                } else {
                    UserFirebaseController.update(usuario);
                    Toast.makeText(this, "Usuário alterado!", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                UserFirebaseController.save(usuario);
                long retorno = crud.create(usuario);
                if (retorno == -1) {
                    Toast.makeText(this, "Erro ao gravar os dados!", Toast.LENGTH_LONG).show();
                } else {
                    (new DB(getBaseContext())).execSQLFromFile(R.raw.sos_acessivel_sql);
                    abrirTelaInicial();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirTelaInicial() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public Usuario popularUsuario() {

        rbTipo = findViewById(rgTipoSanguineo.getCheckedRadioButtonId());
        rbRh = findViewById(rgRhSanguineo.getCheckedRadioButtonId());

        String tipo = TipoSanguineo.values()[rgTipoSanguineo.indexOfChild(rbTipo)].toString();
        String rh = Rh.values()[rgRhSanguineo.indexOfChild(rbRh)].toString();

        Usuario usuario = null;
        try {
            usuario = new Usuario(
                    1, edNome.getText().toString(), (new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())).parse(edDataNascimento.getText().toString()),
                    TipoSanguineo.valueOf(tipo), Rh.valueOf(rh), edEndereco.getText().toString(), edInfMedicas.getText().toString()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public void carregarUsuario() {
        try {
            Usuario usuario = crud.getUsuario();
            edNome.setText(usuario.getNome());
            edEndereco.setText(usuario.getEndereco());
            edDataNascimento.setText(usuario.getDataNascimentoString());
            edInfMedicas.setText(usuario.getInfMedicas());

            rgTipoSanguineo.clearCheck();
            rgRhSanguineo.clearCheck();

            ((RadioButton) rgTipoSanguineo.getChildAt(usuario.getTipoSanguineo().ordinal())).setChecked(true);
            ((RadioButton) rgRhSanguineo.getChildAt(usuario.getRhSanguineo().ordinal())).setChecked(true);

            this.key = usuario.getKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validarCampos() {
        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy", Locale.getDefault());
        format.setLenient(false);

        if (edNome.getText().toString().trim().isEmpty()) {
            setErrorEditText(edNome, getString(R.string.erroNomeNulo));
            return false;
        }

        if (edDataNascimento.getText().toString().trim().isEmpty()) {
            setErrorEditText(edDataNascimento, getString(R.string.erroDataNascimentoNula));
            return false;
        }

        if (!edDataNascimento.getText().toString().matches("^\\d{2}/\\d{2}/\\d{4}$")) {
            setErrorEditText(edDataNascimento, getString(R.string.erroDataNascimentoInvalida));
            return false;
        }

        try {
            Date data = format.parse(edDataNascimento.getText().toString());
            if (data.after(new Date())) {
                setErrorEditText(edDataNascimento, getString(R.string.erroDataNascimentoMaiorQueHoje));
                return false;
            }
        } catch (ParseException e) {
            setErrorEditText(edDataNascimento, getString(R.string.erroDataNascimentoInvalida));
            return false;
        }

        if (edEndereco.getText().toString().trim().isEmpty()) {
            setErrorEditText(edEndereco, getString(R.string.erroEnderecoNulo));
            return false;
        }

        return true;
    }

    private void setErrorEditText(EditText editText, String error) {
        editText.setError(error);
        editText.requestFocus();
    }
}
