package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.ucm.petpal.R;
import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;

/**
 * Clase para que se vean los activity_eventos temporales
 *
 * Created by Juan Lu on 25/02/2016.
 */
public class VerEventos  extends Activity{
    private ListView listaEventos;
    private TextView titulo;
    private Button guardarCambios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_eventos);
        listaEventos = (ListView)findViewById(R.id.listViewEventos);
        titulo = (TextView)findViewById(R.id.tituloEventos);
        guardarCambios = (Button)findViewById(R.id.guardarEvento);
        Bundle bundle = getIntent().getExtras();

       /*if(bundle.getString("hola")!= null)
        {
            //TODO here get the string stored in the string variable and do
            userName.setText(bundle.getString("hola"));
        }*/
        if(bundle.getStringArrayList("listaEventos") != null){
            ArrayList<String> listaE = bundle.getStringArrayList("listaEventos");
            final ArrayList<Integer> listaIds = bundle.getIntegerArrayList("listaIds");
            ArrayList<Integer> listaAsistencia= bundle.getIntegerArrayList("listaAsistencia");
            final ArrayList<Boolean> asistencia= new ArrayList<>();

            for(int i=0; i < listaE.size(); i++) {
                 if(listaAsistencia.get(i)==1)
                        asistencia.add(true);
                 else
                        asistencia.add(false);
            }




            if(listaE.isEmpty()){
                titulo.setText("No tienes ningún evento");
                titulo.setTextColor(Color.GRAY);
                guardarCambios.setActivated(false);
            }
            else{
                guardarCambios.setActivated(true);
                titulo.setText("Próximos eventos");
                //ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaE);
                final AdaptadorEventos adaptador = new AdaptadorEventos(this);
                adaptador.setDatos(listaE);
                adaptador.setDatosCheck(asistencia);
                listaEventos.setAdapter(adaptador);


                //Check
                listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        adaptador.cambiaCheck(position);
                    }
                });

                //Guardar cambios
                guardarCambios.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*ArrayList<TransferEvento> eventosModificados = new ArrayList<TransferEvento>();
                        for(int i=0; i < listaIds.size();i++){
                            TransferEvento eGuardar = new TransferEvento();
                            eGuardar.setId(listaIds.get(i));
                            if(adaptador.getActivos().get(i))
                                eGuardar.setAsistencia(1);
                            else
                                eGuardar.setAsistencia(0);
                            Log.e("TRANSFER: ",eGuardar.getId() + " ACTIVO: " + eGuardar.getAsistencia());
                            eventosModificados.add(eGuardar);
                        }
                    Controlador.getInstancia().ejecutaComando(ListaComandos.GUARDAR_EVENTOS,eventosModificados);*/
                    }
                });


            }

        }



    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "activity_eventos");
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
