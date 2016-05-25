package es.ucm.petpal.negocio.post.imp;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.ucm.petpal.integracion.DBHelper;
import es.ucm.petpal.integracion.Post;
import es.ucm.petpal.integracion.Usuario;
import es.ucm.petpal.negocio.post.SAPost;
import es.ucm.petpal.negocio.post.TransferPost;
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

    @Override
    public void crearPost(TransferPost transferPost) {
        Dao<Post, Integer> daoPost;
        Dao<Usuario, Integer> daoUsuario;
        try {
            daoPost = getHelper().getPostDao();
            daoUsuario = getHelper().getUsuarioDao();

            Usuario u = daoUsuario.queryForAll().get(0);

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

    @Override
    public List<TransferPost> consultarPosts() {
        Dao<Post, Integer> eventos;
        List<Post> listaPosts = null;
        List<TransferPost> transferPosts = new ArrayList<TransferPost>();
        try {
            eventos = getHelper().getPostDao();
            listaPosts= eventos.queryForAll();
            for(int i = 0; i < listaPosts.size(); i++)
                transferPosts.add(new TransferPost(listaPosts.get(i).getId(), listaPosts.get(i).getFecha(),
                        listaPosts.get(i).getTitulo(), listaPosts.get(i).getUbicacion(),
                        listaPosts.get(i).getDescripcion(), listaPosts.get(i).getImagen()));

        } catch (SQLException e) {

        }
        return transferPosts;
    }

}
