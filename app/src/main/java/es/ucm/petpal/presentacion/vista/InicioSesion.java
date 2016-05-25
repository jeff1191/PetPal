package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.ucm.petpal.R;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;

/**
 * Created by Juan Lu on 18/05/2016.
 */
public class InicioSesion extends Activity{

    private EditText passUsuario;
    private EditText correoUsuario;
    private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_acceso);
        passUsuario = (EditText) findViewById(R.id.passAcceso);
        correoUsuario = (EditText) findViewById(R.id.emailAcesso);
    }

    public void realizarInicio(View v){
        //Aqui es donde se deberia comprobar lo de la sincronizacion

        //Leer los datos del "fomulario"
        String pass = String.valueOf(passUsuario.getText());
        String correo = String.valueOf(correoUsuario.getText());

        if(datosValidos(pass,correo)){

            //HABRIA QUE BUSCAR EN EL SERVER Y GUARDAR EL USER EN BBDD
            TransferUsuario crearUsuario = new TransferUsuario();
            crearUsuario.setNombre("LA GUARRA DE JEFF");
            crearUsuario.setAvatar("");
            crearUsuario.setColor("AS_theme_azul");
            crearUsuario.setEmail("soyfacilymelotrago@gargantaprofunda.xxx");
            crearUsuario.setApellidos("LE GUSTAN LOS PENES");
            crearUsuario.setCiudad("RABOLANDIA");
            crearUsuario.setTelefono("696969696");

            Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_USUARIO, crearUsuario);

            startActivity(new Intent(this, MainActivity.class));
        }

    }

    public void volverDecision(View v){
        startActivity(new Intent(this, Acceso.class));
    }

    private boolean datosValidos(String nombre, String correo) {
        if(!nombre.toString().matches("") &&
                !correo.toString().matches("")) {

            if(correo.toString().matches(PATRON_EMAIL)){
                return true;
            }else
                mostrarMensajeError("Campo email inválido");
        }else
            mostrarMensajeError("Algún campo es vacío");

        return false;
    }


    private void mostrarMensajeError(String msg){
        Toast errorNombre =
                Toast.makeText(getApplicationContext(),
                        msg, Toast.LENGTH_SHORT);
        errorNombre.show();
    }
}