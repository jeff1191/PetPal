package es.ucm.petpal.presentacion.controlador.comandos.imp;

import android.util.Log;

import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.presentacion.controlador.comandos.Command;
import es.ucm.petpal.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 14/03/2016.
 */
public class CargarBBDDComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
       /* SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
        saSuceso.cargarTareasBBDD();
        saSuceso.cargarRetoBBDD();
        saSuceso.cargarEventosBBDD();*/
        //Este comando carga por defecto una base de datos


        return null;
    }
}
