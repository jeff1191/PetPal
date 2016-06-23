package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import es.ucm.petpal.R;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;
import es.ucm.petpal.presentacion.web.ConfiguracionWebService;
import es.ucm.petpal.presentacion.web.VolleySingleton;

/**
 * Created by Juan Lu on 18/05/2016.
 */
public class InicioSesion extends Activity{

    private EditText passUsuario;
    private EditText correoUsuario;
    private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    // Atributos
    private RequestQueue requestQueue; //Cola de peticiones
    private JsonObjectRequest jsArrayRequest;
    private static final String TAG = Acceso.class.getSimpleName();
    private TransferUsuario accesoUsuario;
    private boolean acceso =false; //controla si el

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
            requestQueue= Volley.newRequestQueue(Contexto.getInstancia().getContext());

            // Añadiendo correo como parametro
            String newURL = ConfiguracionWebService.GET_LOGIN + "?correo=" + correo+"&pass="+ pass;
            // te traes el usuario y lo insertas en la bdd
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    newURL, (String)null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());

                    try {

                        JSONObject usuario = response.getJSONObject("usuario");
                        accesoUsuario = new TransferUsuario();
                        accesoUsuario.setNombre(usuario.getString("nombre"));
                        accesoUsuario.setAvatar(""); //Traer imagen del servidor, subirla y luego poner la direccion
                        accesoUsuario.setColor("AS_theme_azul");
                        accesoUsuario.setEmail(usuario.getString("email"));
                        accesoUsuario.setApellidos(usuario.getString("apellidos"));
                        accesoUsuario.setCiudad(usuario.getString("ciudad"));
                        accesoUsuario.setTelefono(usuario.getString("telefono"));
                        acceso = true;

                        Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_USUARIO, accesoUsuario);
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Inicio de sesión completado con éxito", Toast.LENGTH_SHORT);

                        toast1.show();
                        startActivity(new Intent(Contexto.getInstancia().getContext(), MainActivity.class));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + "Usuario/contraseña incorrectos",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            });

            VolleySingleton.getInstance(Contexto.getInstancia().getContext()).addToRequestQueue(jsonObjReq);

        }

    }
    public void volverDecision(View v){
        startActivity(new Intent(this, Decision.class));
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
