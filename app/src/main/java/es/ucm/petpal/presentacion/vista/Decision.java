package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import es.ucm.petpal.R;


/**
 * Created by msalitu on 17/03/2016.
 */
public class Decision extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_decision);
    }

    public void irAcceso(View v){
        startActivity(new Intent(this, InicioSesion.class));
    }

    public void irRegistro(View v){
        startActivity(new Intent(this, Registro.class));
    }
}
