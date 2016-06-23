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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
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


    private Bitmap bitmap;

    private Button obtenerUbicacion;
    private Button guardarPost;

    private Location loc;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;

    private TransferUsuario usuarioActivo;

    //String fundamental que contiene la hora en que se hizo el post en el servidor, esto lo hacemos para subir la imagen correctamente
    //La imagen la guardamos con la fecha y hora del servidor por ejemplo: 2016-15-12 12-20-11
    private String fechaHoraImagen;

    private Integer idUsuarioServer;
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


        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            usuarioActivo = (TransferUsuario) getIntent().getExtras().getSerializable("usuarioNuevoPost");

            Log.e("NOMBRE_USER",usuarioActivo.getNombre());
            Log.e("IMAGEN",usuarioActivo.getAvatar());
        }


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
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
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
                imagenPost.setImageBitmap(bitmap);
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
                bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
                imagenPost.setImageBitmap(bitmap);
                rutaImagen=selectedImagePath;
            }
        }
    }
    public void crearPublicacion(View v){
        String tituloP = String.valueOf(titulo.getText());
        String ubicacionP = String.valueOf(ubicacion.getText());
        String descripcionP = String.valueOf(descripcion.getText());

        if(datosPostValidos(tituloP, ubicacionP, descripcionP)){
            TransferPost nuevoPost = new TransferPost();
            nuevoPost.setTitulo(tituloP);
            nuevoPost.setDescripcion(descripcionP);
            nuevoPost.setUbicacion(ubicacionP);
            nuevoPost.setImagen(rutaImagen);
            Log.e("CREANDO DATOS", "LOCAL");
            subirDatosServer(nuevoPost); //subir al servidor a traves de Volley
            Controlador.getInstancia().ejecutaComando(ListaComandos.CREAR_POST, nuevoPost);
        }
    }

    private void subirDatosServer(final TransferPost post){


        String newURL = ConfiguracionWebService.GET_USER_EMAIL + "?correo=" + usuarioActivo.getEmail();
        Log.d("url getUsuario", newURL);
        // / te traes el usuario y lo insertas en la bdd
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                newURL, (String)null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse getUsuario", response.toString());

                try {

                    JSONObject usuario = response.getJSONObject("usuario");
                    idUsuarioServer = Integer.valueOf(usuario.getString("idUsuario"));
                    Log.e("onResponse getUsuario", usuario.toString());
                    insertarPost(post);

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
                VolleyLog.d("obtenerIDUsuarioServer", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Error al subirlo al servidor", Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(Contexto.getInstancia().getContext()).addToRequestQueue(jsonObjReq);


    }

    private void insertarPost(TransferPost post) {
        post.setUsuario(usuarioActivo);
        post.setFecha(new Date());

        ///Peticion al servidor localhost(en este caso)
        HashMap<String, String> map = new HashMap<>();// Mapeo previo
        //map.put("correo", post.getUsuario().getEmail());
        map.put("titulo", post.getTitulo());
        map.put("descripcion", post.getDescripcion());
        map.put("ubicacion", post.getUbicacion());
        map.put("imagen", post.getImagen());
        map.put("fecha", new Date().toString());
        map.put("idUsuario", idUsuarioServer.toString());
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
                                try {
                                    fechaHoraImagen=response.getString("mensaje");
                                    if(bitmap != null)
                                        subirImageServer();
                                    if(!usuarioActivo.getAvatar().equals(""))
                                        subirImageUsuarioServer();

                                    //Volver a la MainActivity
                                    startActivity(new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.e("MOSTRAR_MENSAJE",fechaHoraImagen);
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

    private void subirImageServer(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfiguracionWebService.UPLOAD_IMAGE_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                      //  Toast.makeText(Contexto.getInstancia().getContext(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("imagenPost error", volleyError.getMessage().toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertendo de bitMap a String
                String imagenNuevoPost = getStringImage(bitmap);

                Map<String,String> params = new Hashtable<String, String>();
                params.put("imagenPost", imagenNuevoPost);
                params.put("idUsuario", String.valueOf(idUsuarioServer));
                params.put("fechaSubida", fechaHoraImagen);
             return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //añadir a la cola de peticiones
        requestQueue.add(stringRequest);
    }

    private void subirImageUsuarioServer(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfiguracionWebService.UPLOAD_IMAGE_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Showing toast message of the response
                        //  Toast.makeText(Contexto.getInstancia().getContext(), s , Toast.LENGTH_LONG).show();
                        Log.d("imagenUsuario ok", s);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        Log.d("imagenUsuario error", volleyError.getMessage().toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Bitmap bmUsuario= convertirBitmap(usuarioActivo.getAvatar());

                //Convirtiendo de bitMap a String
                String imagenUsuario = getStringImage(bmUsuario);


                Map<String,String> params = new Hashtable<String, String>();

                //poniendo parametros para las imagenes
                params.put("imagenUsuario", imagenUsuario);
                params.put("correo", usuarioActivo.getEmail());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //añadir a la cola de peticiones
        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void mostrarMensaje(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast exito =
                            Toast.makeText(getApplicationContext(),
                                    "Post realizado correctamente", Toast.LENGTH_SHORT);

                    exito.show();


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

    //Se usa para enviar al servidor la imagen bitmap
    public Bitmap convertirBitmap(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap imagen = BitmapFactory.decodeFile(path, options);
        return imagen;
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

}
