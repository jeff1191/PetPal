package es.ucm.petpal.integracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import es.ucm.petpal.negocio.post.Post;
import es.ucm.petpal.negocio.usuario.Usuario;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "as_user.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<Post, Integer> postDao;
    private Dao<Usuario, Integer> usuarioDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Post.class);
            TableUtils.createTable(connectionSource, Usuario.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(db, connectionSource);
    }

    public Dao<Post, Integer> getPostDao() throws SQLException {
        if (postDao == null) {
            postDao = getDao(Post.class);
        }
        return postDao;
    }
    public Dao<Usuario, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    @Override
    public void close() {
        super.close();
        postDao = null;
        usuarioDao = null;
    }
}

