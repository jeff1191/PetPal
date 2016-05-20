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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import es.ucm.petpal.R;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.controlador.Controlador;
import es.ucm.petpal.presentacion.controlador.ListaComandos;

/**
 * Clase asociada a la vista de activity_configuracion
 *
 * Created by Juan Lu on 24/02/2016.
 */
public class Configuracion extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE =3;
    private static final int SELECCIONAR_GALERIA = 2;
    private static final int CAMARA = 1;
    private static final String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private EditText editarNombre;
    private EditText editarApellidos;
    private EditText editarCiudad;
    private EditText editarTelefono;
    private EditText editarEmail;
    private Button aceptar;
    static String temaActual;
    private String[] nombresColores={ "Azul", "Rojo", "Rosa", "Verde",
            "Negro"};
    private Spinner spinnerColors;
    private String temaParcial;
    private ImageView imagenConfiguracion;
    private String rutaImagen="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_configuracion);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            TransferUsuario usuario = (TransferUsuario) getIntent().getExtras().getSerializable("usuarioConfig");

            editarNombre = (EditText) findViewById(R.id.nombreConfig);
            editarApellidos = (EditText) findViewById(R.id.apellidosConfig);
            editarCiudad = (EditText) findViewById(R.id.ciudadConfig);
            editarTelefono = (EditText) findViewById(R.id.telefonoConfig);
            editarEmail = (EditText) findViewById(R.id.emailConfig);
            aceptar = (Button) findViewById(R.id.envioNuevaConfig);
            temaActual = usuario.getColor();
            temaParcial = temaActual;
            rutaImagen = usuario.getAvatar();
            imagenConfiguracion = (ImageView) findViewById(R.id.imagenConfig);

            spinnerColors = (Spinner) findViewById(R.id.cambiarColor);
            ////////Spinner color ///////
            nombresColoresSistema();
            ArrayAdapter<String> adapter_colores = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, nombresColores);
            adapter_colores
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerColors.setAdapter(adapter_colores);
            spinnerColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinnerColors.setSelection(position);
                    String colorSeleccionado = (String) spinnerColors.getSelectedItem();

                    switch (colorSeleccionado) {
                        case "Azul":
                            temaParcial = "AS_theme_azul";
                            break;

                        case "Rojo":
                            temaParcial = "AS_theme_rojo";
                            break;

                        case "Rosa":
                            temaParcial = "AS_theme_rosa";
                            break;

                        case "Verde":
                            temaParcial = "AS_theme_verde";
                            break;
                        case "Negro":
                            temaParcial = "AS_theme_negro";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ////////////////////////////////////////////////////
            if(!usuario.getAvatar().equals(""))
                imagenConfiguracion.setImageBitmap(BitmapFactory.decodeFile(usuario.getAvatar()));
            else
                imagenConfiguracion.setImageResource(R.drawable.avatar);

            editarNombre.setText(usuario.getNombre());
            editarApellidos.setText(usuario.getApellidos());
            editarCiudad.setText(usuario.getCiudad());
            editarEmail.setText(usuario.getEmail());
            editarTelefono.setText(usuario.getTelefono());

            aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombreU = String.valueOf(editarNombre.getText());
                    String apellidosU = String.valueOf(editarApellidos.getText());
                    String ciudadU = String.valueOf(editarCiudad.getText());
                    String emailU = String.valueOf(editarEmail.getText());
                    String telefonoU = String.valueOf(editarTelefono.getText());

                    if (datosUsuarioValidos(nombreU, emailU)) {
                        TransferUsuario editarUsuario = new TransferUsuario();
                        editarUsuario.setNombre(String.valueOf(editarNombre.getText()));
                        temaActual = temaParcial;
                        editarUsuario.setColor(temaActual);
                        editarUsuario.setAvatar(rutaImagen);
                        editarUsuario.setNombre(nombreU);
                        editarUsuario.setApellidos(apellidosU);
                        editarUsuario.setCiudad(ciudadU);
                        editarUsuario.setTelefono(telefonoU);
                        editarUsuario.setEmail(emailU);

                        Controlador.getInstancia().ejecutaComando(ListaComandos.EDITAR_USUARIO, editarUsuario);
                        Toast.makeText(getApplicationContext(), "El usuario ha sido modificado con éxito", Toast.LENGTH_SHORT).show();
                        Contexto.getInstancia().getContext().startActivity(new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class));

                    } else {
                        Toast errorNombre =
                                Toast.makeText(getApplicationContext(),
                                        "No puede estar vacío el campo nombre", Toast.LENGTH_SHORT);

                        errorNombre.show();
                    }

                }
            });
        }

    }

    public boolean datosUsuarioValidos(String nombre, String correo){

        if(!nombre.toString().matches("") &&
                !correo.toString().matches("")) {

            if(correo.toString().matches(PATRON_EMAIL)){
                return true;
            }else
                mostrarMensajeError("Campo email inválido");
        }else
            mostrarMensajeError("Algún campo obligatorio es vacío");

        return false;
    }

    private void mostrarMensajeError(String msg){
        Toast errorNombre =
                Toast.makeText(getApplicationContext(),
                        msg, Toast.LENGTH_SHORT);
        errorNombre.show();
    }

    private void nombresColoresSistema() {
        switch (temaActual){
            case "AS_theme_azul":
                nombresColores[0]="Azul"; nombresColores[1]="Rojo";nombresColores[2]="Rosa";nombresColores[3]="Verde";nombresColores[4]="Negro";
                break;
            case "AS_theme_rojo":
                nombresColores[0]="Rojo"; nombresColores[1]="Azul";nombresColores[2]="Rosa";nombresColores[3]="Verde";nombresColores[4]="Negro";
                break;
            case "AS_theme_rosa":
                nombresColores[0]="Rosa"; nombresColores[1]="Azul";nombresColores[2]="Rojo";nombresColores[3]="Verde";nombresColores[4]="Negro";
                break;
            case "AS_theme_verde":
                nombresColores[0]="Verde"; nombresColores[1]="Azul";nombresColores[2]="Rojo";nombresColores[3]="Rosa";nombresColores[4]="Negro";
                break;
            case "AS_theme_negro":
                nombresColores[0]="Negro"; nombresColores[1]="Azul";nombresColores[2]="Rojo";nombresColores[3]="Rosa";nombresColores[4]="Verde";
                break;
        }
    }

    public void cargarTema(){
        switch (temaActual){
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
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "configuracion");
    }

    public void cambiarImagenPerfil(View v) {
        final CharSequence[] items = { "Hacer foto", "Elegir de la galeria", "Imagen por defecto" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Configuracion.this);
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
                    imagenConfiguracion.setImageResource(R.drawable.avatar);
                    rutaImagen="";
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
                imagenConfiguracion.setImageBitmap(imagen);
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
                imagenConfiguracion.setImageBitmap(bm);
                rutaImagen=selectedImagePath;
            }
        }
    }
}
