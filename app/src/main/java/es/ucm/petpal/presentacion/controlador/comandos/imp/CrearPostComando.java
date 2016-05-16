package es.ucm.petpal.presentacion.controlador.comandos.imp;

import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.negocio.post.SAPost;
import es.ucm.petpal.negocio.post.TransferPost;
import es.ucm.petpal.presentacion.controlador.comandos.Command;
import es.ucm.petpal.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Juan Lu on 16/05/2016.
 */
public class CrearPostComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAPost sp = FactoriaSA.getInstancia().nuevoSAPost();
        sp.crearPost((TransferPost)datos);
        return null;
    }
}
