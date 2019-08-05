package br.com.luansilveira.sosacessvel.Controller;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.j256.ormlite.dao.BaseDaoImpl;

import java.sql.SQLException;
import java.util.List;

import br.com.luansilveira.sosacessvel.Model.Ocorrencia;
import br.com.luansilveira.sosacessvel.utils.DB;

public class OcorrenciaController extends BaseDaoImpl<Ocorrencia, Integer> {

    private DB db;

    public OcorrenciaController(Context context) throws SQLException {
        super(Ocorrencia.class);
        db = new DB(context);
        setConnectionSource(db.getConnectionSource());
        initialize();
    }

    public List<Ocorrencia> getListaOcorrencias() throws SQLException {
        return this.queryForAll();
    }

    public List<Ocorrencia> getListaOcorrenciasPorStatus(Integer status) throws SQLException {
        return this.queryForEq("status", status);
    }

    @Override
    public int create(Ocorrencia ocorrencia) throws SQLException {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("ocorrencias").push();
        ocorrencia.setKey(dbRef.getKey());

        int retorno = super.create(ocorrencia);

        dbRef.setValue(ocorrencia);

        return retorno;
    }
}
