package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import es.ucm.petpal.R;
import es.ucm.petpal.negocio.post.TransferPost;
import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;

/**
 * Created by Juan Lu on 11/05/2016.
 */
public class NuevoPost extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE =3;
    private static final int SELECCIONAR_GALERIA = 2;
    private static final int CAMARA = 1;

    private ImageView imagenPost;
    private String rutaImagen="";

    private TextView tituloTV;
    private TextView ubicacionTV;
    private TextView descripcionTV;
    private Button guardarPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_crear_publicacion);

        imagenPost = (ImageView) findViewById(R.id.imagenPost);
        tituloTV = (TextView) findViewById(R.id.tituloPost);
        ubicacionTV = (TextView) findViewById(R.id.ubicacionPost);
        descripcionTV = (TextView) findViewById(R.id.descripcionPost);
        guardarPost = (Button) findViewById(R.id.guardarPost);
        final String tituloP = String.valueOf(tituloTV.getText());
        final String ubicacionP = String.valueOf(ubicacionTV.getText());
        final String descripcionP = String.valueOf(descripcionTV.getText());

        guardarPost.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloP = String.valueOf(tituloTV.getText());
                String ubicacionP = String.valueOf(ubicacionTV.getText());
                String descripcionP = String.valueOf(descripcionTV.getText());

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
}
