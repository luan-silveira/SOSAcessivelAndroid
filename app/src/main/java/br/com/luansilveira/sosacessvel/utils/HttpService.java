package br.com.luansilveira.sosacessvel.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

abstract public class HttpService {

    private static Gson gson = null;

    public static void get(Context context, String url, JSONObject param, Response.Listener response, Response.ErrorListener error){
        enviarRequisicaoJson(context, Request.Method.GET, url, param, response, error);
    }

    public static void get(Context context, String url, JSONObject param, Response.Listener response){
        enviarRequisicaoJson(context, Request.Method.GET, url, param, response, null);
    }

    public static void post(Context context, String url, JSONObject param, Response.Listener response, Response.ErrorListener error){
        enviarRequisicaoJson(context, Request.Method.POST, url, param, response, error);
    }

    public static void post(Context context, String url, Object param, Response.Listener response, Response.ErrorListener error){
        enviarRequisicaoJson(context, Request.Method.POST, url, JSON.parseObj(param), response, error);
    }

    public static void enviarRequisicaoJson(Context context, int metodo, String url, JSONObject param, Response.Listener response, Response.ErrorListener error){
        JsonObjectRequest request = new JsonObjectRequest(metodo, url, param, response, error);
        addToQueue(context, request);
    }

    private static void addToQueue(Context context, JsonObjectRequest request){
        Volley.newRequestQueue(context).add(request);
    }


}
