package es.ucm.petpal.presentacion.controlador.comandos.imp;

import es.ucm.petpal.presentacion.controlador.comandos.Command;
import es.ucm.petpal.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Juan Lu on 16/05/2016.
 */
public class VerWebViewComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        return datos;
    }
}
