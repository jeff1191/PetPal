package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.petpal.R;
import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;

/**
 * Created by Juan Lu on 20/05/2016.
 */
public class VerPosts extends Activity{

    private ListView listaPosts;

    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        Contexto.getInstancia().setContext(this);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_posts);

        listaPosts = (ListView) findViewById(R.id.listViewPosts);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            final ArrayList<String> listaP = bundle.getStringArrayList("listaPosts");
            ArrayList<String> imagenesP = bundle.getStringArrayList("imagenesPosts");
            ArrayList<String> ciudadesP = bundle.getStringArrayList("ciudadesPosts");
            final ArrayList<String> descripcionesP = bundle.getStringArrayList("descripcionesPosts");
            ArrayList<String> fechasP = bundle.getStringArrayList("fechasPosts");

            if(listaP.isEmpty()){
                TextView listadoVacio = (TextView) findViewById(R.id.textoListadoVacio);
                listaPosts.setVisibility(View.GONE);
                listadoVacio.setTextColor(Color.GRAY);
                listadoVacio.setVisibility(View.VISIBLE);
            }
            else{
                final AdaptadorPosts adaptador = new AdaptadorPosts(this);
                adaptador.setDatos(listaP);
                adaptador.setDatosImagenes(imagenesP);
                adaptador.setDatosCiudades(ciudadesP);
                adaptador.setDatosFechas(fechasP);
                listaPosts.setAdapter(adaptador);

                //Check
                listaPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //ir a la pantalla de detalle
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                new ContextThemeWrapper(Contexto.getInstancia().getContext(),
                                        R.style.Theme_AppCompat_Light_Dialog));
                        builder.setMessage(descripcionesP.get(position))
                                .setTitle(listaP.get(position));
                        builder.create().show();

                    }
                });
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

    public void ayuda(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "verPosts");
    }
}
