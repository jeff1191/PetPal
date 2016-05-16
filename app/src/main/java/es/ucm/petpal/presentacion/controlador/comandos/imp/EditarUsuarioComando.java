package es.ucm.petpal.presentacion.controlador.comandos.imp;

import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.negocio.usuario.SAUsuario;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.controlador.comandos.Command;
import es.ucm.petpal.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Jeffer on 07/03/2016.
 */
public class EditarUsuarioComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
        TransferUsuario editarUsuario = saUsuario.editarUsuario((TransferUsuario) datos);

        return editarUsuario;
    }
}
