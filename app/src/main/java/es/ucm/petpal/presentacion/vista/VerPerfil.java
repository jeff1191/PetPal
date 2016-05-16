package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.ucm.petpal.R;
import es.ucm.petpal.negocio.usuario.TransferUsuario;

/**
 * Created by Juan Lu on 12/05/2016.
 */
public class VerPerfil extends Activity{

    private ImageView imagenPerfil;
    private TextView nombreTV;
    private TextView apellidosTV;
    private TextView ciudadTV;
    private TextView telefonoTV;
    private TextView emailTV;

    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_perfil);

        nombreTV =(TextView)findViewById(R.id.nombrePerfil);
        apellidosTV = (TextView)findViewById(R.id.apellidosPerfil);
        ciudadTV = (TextView) findViewById(R.id.ciudadPerfil);
        telefonoTV = (TextView)findViewById(R.id.telefonoPerfil);
        emailTV = (TextView) findViewById(R.id.emailPerfil);
        imagenPerfil= (ImageView) findViewById(R.id.imagenPerfil);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            TransferUsuario usuario = (TransferUsuario) getIntent().getExtras().getSerializable("usuario");

            if (usuario != null) {
                if (usuario.getNombre() != null)
                    nombreTV.setText(usuario.getNombre());
                else {
                    nombreTV.setTypeface(null, Typeface.ITALIC);
                    nombreTV.setText("nombre no disponible");
                }

                if (usuario.getApellidos() != null)
                    apellidosTV.setText(usuario.getApellidos());
                else {
                    apellidosTV.setTypeface(null, Typeface.ITALIC);
                    apellidosTV.setText("apellidos no disponibles");
                }

                if (usuario.getCiudad() != null)
                    ciudadTV.setText(usuario.getCiudad());
                else {
                    ciudadTV.setTypeface(null, Typeface.ITALIC);
                    ciudadTV.setText("ciudad no disponible");
                }

                if (usuario.getTelefono() != null)
                    telefonoTV.setText(usuario.getTelefono()+"");
                else {
                    telefonoTV.setTypeface(null, Typeface.ITALIC);
                    telefonoTV.setText("telefono no disponible");
                }

                if (usuario.getEmail() != null)
                    emailTV.setText(usuario.getEmail());
                else {
                    emailTV.setTypeface(null, Typeface.ITALIC);
                    emailTV.setText("email no disponible");
                }

                if (usuario.getAvatar() != null && !usuario.getAvatar().equals(""))
                    imagenPerfil.setImageBitmap(BitmapFactory.decodeFile(usuario.getAvatar()));
                else
                    imagenPerfil.setImageResource(R.drawable.avatar);
            } else {
                findViewById(R.id.tituloNombre).setVisibility(View.GONE);
                findViewById(R.id.bloqueApellidos).setVisibility(View.GONE);
                findViewById(R.id.bloqueCiudad).setVisibility(View.GONE);
                findViewById(R.id.bloqueTelefono).setVisibility(View.GONE);
                findViewById(R.id.bloqueEmail).setVisibility(View.GONE);

                imagenPerfil.setImageResource(R.drawable.avatar);

                nombreTV.setTypeface(null, Typeface.ITALIC);
                nombreTV.setGravity(Gravity.CENTER);
                nombreTV.setText("Datos del usuario no disponibles");
            }
        }

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

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

}
