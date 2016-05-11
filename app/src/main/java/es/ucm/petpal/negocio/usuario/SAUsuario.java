/**
 * 
 */
package es.ucm.petpal.negocio.usuario;

public interface SAUsuario {
	TransferUsuario editarUsuario(TransferUsuario datos);

	public void sincronizar();

	void crearUsuario(TransferUsuario transferUsuario);

	TransferUsuario consultarUsuario();

	void enviarCorreo();
}