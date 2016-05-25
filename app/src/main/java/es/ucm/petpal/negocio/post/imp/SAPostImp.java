package es.ucm.petpal.negocio.post.imp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import es.ucm.petpal.R;
import es.ucm.petpal.integracion.DBHelper;
import es.ucm.petpal.integracion.Post;
import es.ucm.petpal.integracion.Usuario;
import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.negocio.post.SAPost;
import es.ucm.petpal.negocio.post.TransferPost;
import es.ucm.petpal.negocio.usuario.SAUsuario;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.vista.Contexto;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class SAPostImp implements SAPost {

    private final static String NOMBRE_DOCUMENTO = "Informe.pdf";
    private final static String NOMBRE_DIRECTORIO = "AS";
    private DBHelper mDBHelper;

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(Contexto.getInstancia().getContext().getApplicationContext(), DBHelper.class);
        }
        return mDBHelper;
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    public static File getRuta() {
        // El fichero será almacenado en un directorio dentro del directorio Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }
    
    @Override
    public void crearPost(TransferPost transferPost) {
        Dao<Post, Integer> daoPost;
        Dao<Usuario, Integer> daoUsuario;
        try {
            daoPost = getHelper().getPostDao();
            daoUsuario = getHelper().getUsuarioDao();

            Usuario u = daoUsuario.queryForId(1);

            Post post = new Post();
            post.setTitulo(transferPost.getTitulo());
            post.setImagen(transferPost.getImagen());
            post.setUbicacion(transferPost.getUbicacion());
            post.setDescripcion(transferPost.getDescripcion());
            post.setFecha(Calendar.getInstance().getTime());
            post.setUsuario(u);

            daoPost.create(post);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
