package es.ucm.petpal.presentacion.vista;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

import es.ucm.petpal.R;

/**
 * Created by Juan Lu on 10/03/2016.
 */
public class NotificacionPublicidad{

    public static void lanzarNotificacion(String titulo, String texto) {

        Log.e("prueba", "Empieza a crear la notificacion publicidad...");
        /*
        It gets current system time. Then I'm reading only last 4 digits from it.
         I'm using it to create unique id every time notification is displayed.
         So the probability of getting same or reset of notification id will be avoided.
         */
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);

        Log.e("prueba", "Notificacion con el ID..." + notificationId);

        NotificationCompat.Builder n =
                new NotificationCompat.Builder(Contexto.getInstancia().getContext())
                        .setSmallIcon(R.drawable.logo_notificacion)
                        .setContentTitle(titulo)    //Titulo
                        .setContentText(texto)      //Texto
                        .setPriority(Notification.PRIORITY_MAX)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{200, 300, 200, 300, 200})
                        .setLights(Color.YELLOW, 3000, 3000);


        NotificationManager notificationManager =
                (NotificationManager) Contexto.getInstancia().getContext().getSystemService(
                        Contexto.getInstancia().getContext().NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, n.build());

    }
}
