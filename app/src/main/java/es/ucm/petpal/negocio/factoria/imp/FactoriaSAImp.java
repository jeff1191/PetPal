/**
 * 
 */
package es.ucm.petpal.negocio.factoria.imp;

import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.negocio.post.SAPost;
import es.ucm.petpal.negocio.post.imp.SAPostImp;
import es.ucm.petpal.negocio.usuario.SAUsuario;
import es.ucm.petpal.negocio.usuario.imp.SAUsuarioImp;


public class FactoriaSAImp extends FactoriaSA {

	public SAUsuario nuevoSAUsuario() {
		return  new SAUsuarioImp();
	}
	
	public SAPost nuevoSAPost() {
		return new SAPostImp();
	}
}