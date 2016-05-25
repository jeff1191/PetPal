package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import es.ucm.petpal.R;
import es.ucm.petpal.negocio.post.TransferPost;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;
import es.ucm.petpal.presentacion.web.ConfiguracionWebService;
import es.ucm.petpal.presentacion.web.VolleySingleton;

/**
 * Created by Juan Lu on 11/05/2016.
 */
public class NuevoPost extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE =3;
    private static final int SELECCIONAR_GALERIA = 2;
    private static final int CAMARA = 1;

    private ImageView imagenPost;
    private String rutaImagen="";
    private EditText titulo;
    private TextView ubicacion;
    private EditText descripcion;

    private Button obtenerUbicacion;
    private Button guardarPost;

    private Location loc;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        Contexto.getInstancia().setContext(this);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_crear_publicacion);

        imagenPost = (ImageView) findViewById(R.id.imagenPost);
        titulo = (EditText) findViewById(R.id.tituloPost);
        ubicacion = (TextView) findViewById(R.id.textoUbicacion);
        obtenerUbicacion = (Button) findViewById(R.id.obtenerUbicacion);
        descripcion = (EditText) findViewById(R.id.descripcionPost);
        guardarPost = (Button) findViewById(R.id.guardarPost);

        obtenerUbicacion.setVisibility(View.VISIBLE);
        ubicacion.setVisibility(View.GONE);

        // Configuración del sistema de localización
        final LocationManager mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //variable para ver si el estado del GPS
        isGPSEnabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        //variable para ver si el estado de la red
        isNetworkEnabled =  mLocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        final MyLocationListener mLocListener = new MyLocationListener();
        mLocListener.setMainActivity(NuevoPost.this);

        obtenerUbicacion.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hago un pequeño truco por si el gps nos deja tirados
                if (!isGPSEnabled && !isNetworkEnabled) {
                    mostrarMensajeError("No hay forma de obtener la localizacion");
                }
                else{
                    if (isGPSEnabled) {
                        Log.e("meterseGPS", "ubicacion a buscar");
                        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
                        if(mLocManager != null){
                            loc = mLocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(loc != null){
                                try {
                                    Log.e("meterseGPS","dentro");
                                    Geocoder geoCoder = new Geocoder(Contexto.getInstancia().getContext(), Locale.getDefault());
                                    StringBuilder builder = new StringBuilder();
                                    List<Address> address = geoCoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                                    int maxLines = address.get(0).getMaxAddressLineIndex();
                                    for (int i = 0; i < maxLines; i++) {
                                        String addressStr = address.get(0).getAddressLine(i);
                                        builder.append(addressStr);
                                        builder.append(" ");
                                    }
                                    String finalAddress = builder.toString(); //This is the complete address.
                                    Log.e("meterseGPS", finalAddress);
                                    ubicacion.setText(finalAddress);
                                    obtenerUbicacion.setVisibility(View.GONE);
                                    ubicacion.setVisibility(View.VISIBLE);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                            else{
                                Log.e("meterseGPS","loc fallo");
                                Log.e("meterseRED", "ubicacion a buscar");
                                mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocListener);
                                if (mLocManager != null) {
                                    loc = mLocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                    if (loc != null) {
                                        try {
                                            Log.e("meterseRED", "dentro");
                                            Geocoder geoCoder = new Geocoder(Contexto.getInstancia().getContext(), Locale.getDefault());
                                            StringBuilder builder = new StringBuilder();
                                            Log.e("meterseRED", loc.getLatitude()+" "+loc.getLongitude());
                                            List<Address> address = geoCoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                                            Log.e("meterseRED", "coge bien lat y long");
                                            Log.e("meterseRED", loc.getLatitude()+" "+loc.getLongitude());
                                            int maxLines = address.get(0).getMaxAddressLineIndex();
                                            for (int i = 0; i < maxLines; i++) {
                                                String addressStr = address.get(0).getAddressLine(i);
                                                builder.append(addressStr);
                                                builder.append(" ");
                                            }
                                            String finalAddress = builder.toString(); //This is the complete address.
                                            Log.e("meterseRED", finalAddress);
                                            ubicacion.setText(finalAddress);
                                            obtenerUbicacion.setVisibility(View.GONE);
                                            ubicacion.setVisibility(View.VISIBLE);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                        mostrarMensajeError("El GPS no esta activado");

                }
            }
        });
        guardarPost.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloP = String.valueOf(titulo.getText());
                String ubicacionP = String.valueOf(ubicacion.getText());
                String descripcionP = String.valueOf(descripcion.getText());

                if(datosPostValidos(tituloP, ubicacionP, descripcionP)){
                    TransferPost nuevoPost = new TransferPost();
                    nuevoPost.setTitulo(tituloP);
                    nuevoPost.setDescripcion(descripcionP);
                    nuevoPost.setUbicacion(ubicacionP);
                    nuevoPost.setImagen(rutaImagen);
                    Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_POST, nuevoPost);
                    Toast.makeText(getApplicationContext(), "El post ha sido creado con éxito", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class));
                }
            }
        });

    }

    public boolean datosPostValidos(String titulo, String ubicacion, String descripcion){

        if(!titulo.toString().matches("") && !ubicacion.toString().matches("") && !descripcion.toString().matches(""))
            return true;
        else
            mostrarMensajeError("Algún campo es vacío");

        return false;
    }

    private void mostrarMensajeError(String msg){
        Toast errorNombre =
                Toast.makeText(getApplicationContext(),
                        msg, Toast.LENGTH_SHORT);
        errorNombre.show();
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
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "nuevoPost");
    }

    public void nuevaImagenPost(View v) {
        final CharSequence[] items = { "Hacer foto", "Elegir de la galeria", "Imagen por defecto" };
        AlertDialog.Builder builder = new AlertDialog.Builder(NuevoPost.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Hacer foto")) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMARA);
                } else if (items[item].equals("Elegir de la galeria")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECCIONAR_GALERIA);
                } else if (items[item].equals("Imagen por defecto")) {
                    imagenPost.setImageResource(R.drawable.avatar);
                    rutaImagen = "";
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMARA) {
                Bitmap imagen = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imagen.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imagenPost.setImageBitmap(imagen);
                rutaImagen = destination.getPath();
            } else if (requestCode == SELECCIONAR_GALERIA) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                imagenPost.setImageBitmap(bm);
                rutaImagen=selectedImagePath;
            }
        }
    }

    public void crearPublicacion(View v){
        Toast errorNombre =
                Toast.makeText(getApplicationContext(),
                        "AQUI VIENE LA MAGIA", Toast.LENGTH_SHORT);

        errorNombre.show();

        TransferUsuario usuario = new TransferUsuario();
        usuario.setId(100);
        usuario.setNombre("PEPE");
        usuario.setAvatar("");
        TransferPost post = new TransferPost();
        post.setImagen(rutaImagen);
        post.setId(100);
        post.setTitulo(titulo.getText().toString());
        post.setDescripcion(descripcion.getText().toString());
        post.setUbicacion(ubicacion.getText().toString());
        post.setUsuario(usuario);
        post.setFecha(new Date());

    ///Peticion al servidor localhost(en este caso)
        HashMap<String, String> map = new HashMap<>();// Mapeo previo
        map.put("idUsuario", post.getUsuario().getId().toString());
        map.put("titulo", post.getTitulo());
        map.put("descripcion", post.getDescripcion());
        map.put("ubicacion", post.getUbicacion());
        map.put("imagen", post.getImagen());
        map.put("fecha", new Date().toString());
        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Depurando objeto Json...
        Log.d("NUEVO_POST", jobject.toString());


        // Actualizar datos en el servidor
        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        ConfiguracionWebService.INSERT_POST,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                mostrarMensaje(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("NUEVO_POST", "Error Volley: " + error.getMessage());
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );



    }

    private void mostrarMensaje(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast exito =
                            Toast.makeText(getApplicationContext(),
                                    "Post realizado correctamente", Toast.LENGTH_SHORT);

                    exito.show();
                    this.finish();
                    break;

                case "2":
                    // Mostrar mensaje error ( Nº 2 es el mensaje de error enviado por el webServices
                    Toast errorNombre =
                            Toast.makeText(getApplicationContext(),
                                    "Error al realizar el post", Toast.LENGTH_SHORT);

                    errorNombre.show();
                    // Terminar actividad
                    this.finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Location listener para detectar la ubicación
    public class MyLocationListener implements LocationListener {
        NuevoPost mainActivity;

        public NuevoPost getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(NuevoPost mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location location) {
            loc = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Este mŽtodo se ejecuta cada vez que se detecta un cambio en el
            // status del proveedor de localizaci—n (GPS)
            // Los diferentes Status son:
            // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
            // TEMPORARILY_UNAVAILABLE -> Temp˜ralmente no disponible pero se
            // espera que este disponible en breve
            // AVAILABLE -> Disponible
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}
