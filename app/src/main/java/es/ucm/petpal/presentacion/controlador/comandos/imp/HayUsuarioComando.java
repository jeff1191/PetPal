package es.ucm.petpal.presentacion.controlador.comandos.imp;


import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.negocio.usuario.SAUsuario;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.controlador.comandos.Command;
import es.ucm.petpal.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 11/03/2016.
 */
public class HayUsuarioComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAUsuario su = FactoriaSA.getInstancia().nuevoSAUsuario();
        TransferUsuario transferUsuario = su.consultarUsuario();
        return transferUsuario;
    }
}
