package br.com.luansilveira.sosacessvel.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Geolocalizacao {

    private Context context;
    private Location local;
    private GeolocalizacaoListener listener;

    public Geolocalizacao(Context context) {
        this.context = context;
    }

    public void getLocalizacao(){
        FusedLocationProviderClient fusedLocation = LocationServices.getFusedLocationProviderClient(context);

        try {
            final Task localizacao = fusedLocation.getLastLocation();
            localizacao.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        local = (Location) task.getResult();
                        listener.onLocalizacaoEncontrada(local);
                    }
                }
            });

        } catch (SecurityException e) {
            Log.e("Exception", e.getMessage());
        }
    }

    public boolean isPermissaoLocalizacao(){
        return ((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED));
    }

    public interface GeolocalizacaoListener {
        void onLocalizacaoEncontrada(Location local);
    }

    public Geolocalizacao setListener(GeolocalizacaoListener listener) {
        this.listener = listener;

        return this;
    }
}
