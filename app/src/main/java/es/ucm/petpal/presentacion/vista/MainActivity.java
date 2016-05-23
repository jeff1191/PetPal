package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import es.ucm.petpal.R;
import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*Command c = FactoriaComandos.getInstancia().getCommand(ListaComandos.CONSULTAR_USUARIO);
        TransferUsuario usuario = new TransferUsuario();
        try {
            usuario = (TransferUsuario) c.ejecutaComando(null);
        } catch (commandException e) {
            e.printStackTrace();
        }
        // Completa los datos del usuario que se muestran en esta pantalla
        Configuracion.temaActual = usuario.getColor();*/
        cargarTema();
        setContentView(R.layout.activity_main);

        // Esto es para solventar un error al enviar el correo
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void personalizacion(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.CONFIGURACION, null);
    }

    public void ayuda(View v) {
       Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "principal");
    }

    public void verRedSocial(View v) {
        Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_WEB_VIEW, null);
    }

    public void verPerfil(View v) {
        Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, null);
    }

    public void nuevoPost(View v) {
        Contexto.getInstancia().getContext().startActivity(new Intent(
                Contexto.getInstancia().getContext().getApplicationContext(), NuevoPost.class));
    }

    public void verPosts(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.VER_POSTS, null);
    }

    public void cargarTema(){
        switch (Configuracion.temaActual){
            case "AS_theme_azul":
                setTheme(R.style.AS_tema_azul);
                break;
            case "AS_theme_rojo":
                setTheme(R.style.AS_tema_rojo);
                break;
            case "AS_theme_rosa":
                setTheme(R.style.AS_tema_rosa);
                break;
            case "AS_theme_verde":
                setTheme(R.style.AS_tema_verde);
                break;
            case "AS_theme_negro":
                setTheme(R.style.AS_tema_negro);
                break;
        }
    }
}
