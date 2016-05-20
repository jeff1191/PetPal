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
 * Created by Juan Lu on 15/03/2016.
 */
public class Registro extends Activity {

    private EditText nombreUsuario;
    private EditText apellidosUsuario;
    private EditText passUsuario;
    private EditText ciudadUsuario;
    private EditText telefonoUsuario;
    private EditText emailUsuario;

    private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PATRON_TELEFONO = "^[0-9]{9}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registro);
        nombreUsuario = (EditText) findViewById(R.id.nombreReg);
        apellidosUsuario = (EditText) findViewById(R.id.apellidosReg);
        passUsuario = (EditText) findViewById(R.id.passReg);
        ciudadUsuario = (EditText) findViewById(R.id.ciudadReg);
        telefonoUsuario = (EditText) findViewById(R.id.telefononReg);
        emailUsuario = (EditText) findViewById(R.id.emailReg);

    }

    public void realizarRegistro(View v){
        //Aqui es donde se deberia comprobar lo de la sincronizacion

        //Leer los datos del "fomulario"
        String nombre = String.valueOf(nombreUsuario.getText());
        String apellidos = String.valueOf(apellidosUsuario.getText());
        String pass = String.valueOf(passUsuario.getText());
        String ciudad = String.valueOf(ciudadUsuario.getText());
        String telefono = String.valueOf(telefonoUsuario.getText());
        String email = String.valueOf(emailUsuario.getText());

        if(datosValidos(nombre,pass,email,telefono)){
            TransferUsuario crearUsuario = new TransferUsuario();
            crearUsuario.setNombre(nombre);
            crearUsuario.setAvatar("");
            crearUsuario.setColor("AS_theme_azul");
            crearUsuario.setEmail(email);
            crearUsuario.setApellidos(apellidos);
            crearUsuario.setCiudad(ciudad);
            if(!telefono.toString().matches("")){
                crearUsuario.setTelefono(Integer.parseInt(telefono));
            }
            else{
                crearUsuario.setTelefono(0);
            }

            Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_USUARIO, crearUsuario);
            
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    public void volverDecision(View v){
        startActivity(new Intent(this, Decision.class));
    }

    private boolean datosValidos(String nombre, String pass, String correo, String telefono) {
        if(!nombre.toString().matches("") &&
                !correo.toString().matches("") && !pass.toString().matches("")) {

            if(correo.toString().matches(PATRON_EMAIL)){
                if(!telefono.toString().matches("")){
                    if(telefono.toString().matches(PATRON_TELEFONO)){
                        return true;
                    }
                    else
                        mostrarMensajeError("Campo telefono inválido");
                }
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
