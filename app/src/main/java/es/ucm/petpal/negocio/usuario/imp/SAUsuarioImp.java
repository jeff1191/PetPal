/**
 * 
 */
package es.ucm.petpal.negocio.usuario.imp;


import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import es.ucm.petpal.integracion.DBHelper;
import es.ucm.petpal.integracion.Usuario;
import es.ucm.petpal.negocio.usuario.SAUsuario;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.vista.Contexto;

public class SAUsuarioImp implements SAUsuario {

	private DBHelper mDBHelper;

	private DBHelper getHelper() {
		if (mDBHelper == null) {
			mDBHelper = OpenHelperManager.getHelper(Contexto.getInstancia().getContext().getApplicationContext(), DBHelper.class);
		}
		return mDBHelper;
	}
	
	public TransferUsuario editarUsuario(TransferUsuario datos) {
		Dao<Usuario, Integer> daoUsuario;
		TransferUsuario ret = new TransferUsuario();
		try {
			daoUsuario = getHelper().getUsuarioDao();
			if (daoUsuario.idExists(1)) {
				Usuario usuario = daoUsuario.queryForId(1);
				usuario.setNombre(datos.getNombre());
				usuario.setAvatar(datos.getAvatar());
				usuario.setColor(datos.getColor());
				usuario.setCiudad(datos.getCiudad());
				usuario.setTelefono(datos.getTelefono());
				usuario.setApellidos(datos.getApellidos());
				usuario.setEmail(datos.getEmail());
				daoUsuario.update(usuario);
			}else
				return null;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return datos;
	}
	
	public void sincronizar() {
		
	}

	@Override
	public void crearUsuario(TransferUsuario transferUsuario) {
		
		Dao<Usuario, Integer> daoUsuario;
		try {
			daoUsuario = getHelper().getUsuarioDao();

			Usuario usuario = new Usuario();

			usuario.setNombre(transferUsuario.getNombre());
			usuario.setApellidos(transferUsuario.getApellidos());
			usuario.setAvatar(transferUsuario.getAvatar());
			usuario.setCiudad(transferUsuario.getCiudad());
			usuario.setTelefono(transferUsuario.getTelefono());
			usuario.setEmail(transferUsuario.getEmail());
			usuario.setColor(transferUsuario.getColor());

			daoUsuario.create(usuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TransferUsuario consultarUsuario() {
		Dao<Usuario, Integer> daoUsuario;
		TransferUsuario transferUsuario = new TransferUsuario();
		try {

			daoUsuario = getHelper().getUsuarioDao();

			if (!daoUsuario.idExists(1))
				return null;
			else {
				Usuario u = daoUsuario.queryForId(1);
				// metemos los datos en un transfer
				transferUsuario.setId(u.getId());
				if (u.getNombre() != null)
					transferUsuario.setNombre(u.getNombre());
				if (u.getApellidos() != null)
					transferUsuario.setApellidos(u.getApellidos());
				if (u.getAvatar() != null)
					transferUsuario.setAvatar(u.getAvatar());
				if (u.getTelefono() != null)
					transferUsuario.setTelefono(u.getTelefono());
				if (u.getCiudad() != null)
					transferUsuario.setCiudad(u.getCiudad());
				if (u.getEmail() != null)
					transferUsuario.setEmail(u.getEmail());
				if (u.getColor() != null)
					transferUsuario.setColor(u.getColor());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transferUsuario;
	}

}