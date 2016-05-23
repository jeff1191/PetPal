/**
 * 
 */
package es.ucm.petpal.negocio.usuario;

public interface SAUsuario {

	TransferUsuario editarUsuario(TransferUsuario datos);

	void crearUsuario(TransferUsuario transferUsuario);

	TransferUsuario consultarUsuario();
}