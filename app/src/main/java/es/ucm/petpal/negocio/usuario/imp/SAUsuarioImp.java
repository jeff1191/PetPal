/**
 * 
 */
package es.ucm.petpal.negocio.usuario.imp;


import android.content.Intent;
import android.net.Uri;

import es.ucm.petpal.negocio.usuario.SAUsuario;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import es.ucm.petpal.integracion.DBHelper;
import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.negocio.post.SAPost;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.negocio.usuario.Usuario;
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
				usuario.setTono(datos.getTono());
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

	/*
	puntuacion = (10 * nºtareas positivas / nº tareas totales
	* */
	@Override
	public Integer calcularPuntuacion() {
	/*	Integer ret;
		// Se cogen las tareas de la BBDD
		SASuceso ss = FactoriaSA.getInstancia().nuevoSASuceso();
		List<TransferTarea> tareas = ss.consultarTareas();
		// Se cuentan las positivas y se realiza el calculo
		int positivas = 0;
		for (int i = 0; i < tareas.size(); i++)
			if (tareas.get(i).getNumSi() - tareas.get(i).getNumNo() >= 0)
				positivas++;
		ret = 10*positivas/tareas.size();
		// Se actualiza la puntuacion en la BBDD
		Dao<Usuario, Integer> daoUsuario;
		try {
			daoUsuario = getHelper().getUsuarioDao();
			Usuario usuario = daoUsuario.queryForId(1);
			usuario.setPuntuacionAnterior(usuario.getPuntuacion());
			usuario.setPuntuacion(ret);
			daoUsuario.update(usuario);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;*/
		return 0;
	}

	@Override
	public void crearUsuario(TransferUsuario transferUsuario) {
		
		Dao<Usuario, Integer> daoUsuario;
		try {
			daoUsuario = getHelper().getUsuarioDao();

			Usuario usuario = new Usuario();

			// actualizamos los valores del nuevo usuario con los introducidos o por defecto
			if (transferUsuario.getNombre() != null)
				usuario.setNombre(transferUsuario.getNombre());
			else
				usuario.setNombre("Usuario");

			if (transferUsuario.getCorreo() != null)
				usuario.setCorreo(transferUsuario.getCorreo());

			if (transferUsuario.getAvatar() != null)
				usuario.setAvatar(transferUsuario.getAvatar());

			if (transferUsuario.getPuntuacion() != null)
				usuario.setPuntuacion(transferUsuario.getPuntuacion());
			else
				usuario.setPuntuacion(0);

			if (transferUsuario.getPuntuacionAnterior() != null)
				usuario.setPuntuacionAnterior(transferUsuario.getPuntuacionAnterior());
			else
				usuario.setPuntuacionAnterior(0);

			if (transferUsuario.getColor() != null)
				usuario.setColor(transferUsuario.getColor());

			if (transferUsuario.getTono() != null)
				usuario.setTono(transferUsuario.getTono());

			if (transferUsuario.getNombreTutor() != null)
				usuario.setNombreTutor(transferUsuario.getNombreTutor());

			if (transferUsuario.getCorreoTutor() != null)
				usuario.setCorreoTutor(transferUsuario.getCorreoTutor());

			// se crea la fila en la tabla de la BBDD
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
				if (u.getCorreo() != null)
					transferUsuario.setCorreo(u.getCorreo());
				if (u.getAvatar() != null)
					transferUsuario.setAvatar(u.getAvatar());
				if (u.getPuntuacion() != null)
					transferUsuario.setPuntuacion(u.getPuntuacion());
				if (u.getPuntuacionAnterior() != null)
					transferUsuario.setPuntuacionAnterior(u.getPuntuacionAnterior());
				if (u.getColor() != null)
					transferUsuario.setColor(u.getColor());
				if (u.getTono() != null)
					transferUsuario.setTono(u.getTono());
				if (u.getNombreTutor() != null)
					transferUsuario.setNombreTutor(u.getNombreTutor());
				if (u.getCorreoTutor() != null)
					transferUsuario.setCorreoTutor(u.getCorreoTutor());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transferUsuario;
	}

	@Override
	public void enviarCorreo() {

		String mail = "";
		String name = "";
		Dao<Usuario, Integer> daoUsuario;
		try {
			daoUsuario = getHelper().getUsuarioDao();
			Usuario u = daoUsuario.queryForId(1);
			mail= u.getCorreo();
			name = u.getNombre();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Enviar correo abriendo aplicación/////////////////////////////////////////////////////

		//Instanciamos un Intent del tipo ACTION_SEND
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		//Definimos la tipologia de datos del contenido dle Email en este caso text/html
		emailIntent.setType("application/pdf");
		// Indicamos con un Array de tipo String las direcciones de correo a las cuales
		//queremos enviar el texto
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
		// Definimos un titulo para el Email
		emailIntent.putExtra(android.content.Intent.EXTRA_TITLE, "Informe AS");
		// Definimos un Asunto para el Email
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Informe AS");
		// Obtenemos la referencia al texto y lo pasamos al Email Intent
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "¡Hola " + name + "!\n " +
				"Este es tu progreso hasta el momento. Sigue esforzándote para continuar mejorando."
		+ "\n¡Ánimo!" + "\n\nEnviado desde AS");

		Uri uri = Uri.parse( new File("file://" + "/sdcard/Download/AS/Informe.pdf").toString());
		emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

		Contexto.getInstancia().getContext().startActivity(emailIntent);

		///////////////////////////////////////////////////////////////////////////////////////////

	}


}