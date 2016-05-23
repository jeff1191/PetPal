package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import es.ucm.petpal.R;
import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;

public class Bienvenido extends Activity {
    private static final long DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_bienvenido);


        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Bienvenido", Toast.LENGTH_SHORT);

        toast1.show();


        //Hacer la comprobacion aqui, si no hay user que vaya a decision,
        //si hay user que vaya directamente al main
        //Tambien hay que darle un valor al tema actual: Configuracion.temaActual = usuario.getColor()
        //Aqui si ya hay user, en registro poner el default y en acceso el del user
        Contexto.getInstancia().setContext(this);

        Controlador.getInstancia().ejecutaComando(ListaComandos.HAY_USUARIO, null);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            if (!bundle.getBoolean("existe")) {//No hay usuario
                Configuracion.temaActual = bundle.getString("color");
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent().setClass(
                                Bienvenido.this, Decision.class);
                        startActivity(mainIntent);
                        finish();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, DELAY);
            } else { // hay un usuario
                Configuracion.temaActual = bundle.getString("color");
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent().setClass(
                                Bienvenido.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, DELAY);
            }
        }
    }
}
