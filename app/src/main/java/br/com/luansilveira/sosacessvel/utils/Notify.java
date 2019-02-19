package br.com.luansilveira.sosacessvel.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Classe utilizada para implementar notificações no Android.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class Notify {

    public static final int IMPORTANCE_DEFAULT = NotificationManager.IMPORTANCE_DEFAULT;
    public static final int IMPORTANCE_LOW = NotificationManager.IMPORTANCE_LOW;
    public static final int IMPORTANCE_HIGH = NotificationManager.IMPORTANCE_HIGH;
    public static final int IMPORTANCE_MIN = NotificationManager.IMPORTANCE_MIN;
    public static final int IMPORTANCE_MAX = NotificationManager.IMPORTANCE_MAX;

    private NotificationManager manager;
    private Context context;

    public Notify(Context context) {
        this.context = context;
        this.manager =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Função utilizada para criar uma notificação e mostrá-la na barra de notificações.
     *
     * @param contentIntent
     * @param icon
     * @param title
     * @param text
     */
    public void criarNotificacao(Intent contentIntent, int icon, CharSequence title, CharSequence text){
        this.criarNotificacao(contentIntent, icon, title, text, null);
    }

    public void criarNotificacao(Intent contentIntent, int icon, CharSequence title, CharSequence text, String id_canal){
        int id = 1;

        PendingIntent p = getPendingIntent(id, contentIntent, context);

        NotificationCompat.Builder notificacao = null;
        if(id_canal != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificacao = new NotificationCompat.Builder(context, id_canal);
            } else {
                notificacao = new NotificationCompat.Builder(context);
            }
        }
        notificacao.setSmallIcon(icon);
        notificacao.setContentTitle(title);
        notificacao.setContentText(text);
        notificacao.setContentIntent(p);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(id, notificacao.build());
    }

    public NotificationManager getManager() {
        return manager;
    }

    private PendingIntent getPendingIntent(int id, Intent intent, Context context){
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(intent.getComponent());
        stackBuilder.addNextIntent(intent);

        PendingIntent p = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);

        return p;
    }

    /**
     * Função utilizada para criar um canal de notificação, obrigatório a partir da versão 8 (Oreo) do Android.
     *
     * @param id
     * @param nome
     * @param importancia
     * @return
     */

    public NotificationChannel criarCanalNotificacao(String id, CharSequence nome, int importancia){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(id, nome, importancia);
            this.manager.createNotificationChannel(canal);

            return canal;
        } else {
            return null;
        }
    }

}
