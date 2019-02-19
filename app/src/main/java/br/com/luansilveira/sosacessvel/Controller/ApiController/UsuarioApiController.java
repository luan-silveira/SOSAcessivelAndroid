package br.com.luansilveira.sosacessvel.Controller.ApiController;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import br.com.luansilveira.sosacessvel.Model.Usuario;
import br.com.luansilveira.sosacessvel.utils.HttpService;

public class UsuarioApiController extends ApiController {

    public UsuarioApiController(Context context) {
        super(context);
    }

    public void create(Usuario usuario) {
        String url = APP_URL + "paciente/create";
        HttpService.post(context, url, usuario, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
//                Toast.makeText(context.getApplicationContext(), "Response: " + response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String erro = "Erro VolleyError: " + error.getMessage();
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    erro += "\r\nStatus: " + response.statusCode + "\r\n" +
                            new String(response.data);
                }

                Toast.makeText(context.getApplicationContext(), erro, Toast.LENGTH_LONG).show();
            }
        });
    }

}
