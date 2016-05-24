package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;

/**
 * Created by Juan Lu on 23/05/2016.
 */
public class Decision extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hacer la comprobacion aqui, si no hay user que vaya a decision,
        //si hay user que vaya directamente al main
        //Tambien hay que darle un valor al tema actual: Configuracion.temaActual = usuario.getColor()
        Contexto.getInstancia().setContext(this);

        Controlador.getInstancia().ejecutaComando(ListaComandos.HAY_USUARIO, null);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Configuracion.temaActual = bundle.getString("color");

            NotificacionPublicidad.lanzarNotificacion("Publicidad", "20% dto. en recoge pelos, solo en FROSTY'S!");

            if (!bundle.getBoolean("existe"))
                startActivity(new Intent(this, Acceso.class));
             else
                startActivity(new Intent(this, MainActivity.class));

        }
        finish();
    }
}
