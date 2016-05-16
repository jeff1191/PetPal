package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.controlador.ListaComandos;
import es.ucm.petpal.presentacion.controlador.comandos.Command;
import es.ucm.petpal.presentacion.controlador.comandos.exceptions.commandException;
import es.ucm.petpal.presentacion.controlador.comandos.factoria.FactoriaComandos;


/**
 * Created by msalitu on 17/03/2016.
 */
public class Decision extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Contexto.getInstancia().setContext(this);
        Command c = FactoriaComandos.getInstancia().getCommand(ListaComandos.CONSULTAR_USUARIO);
        TransferUsuario cargarUsuario;
        try {
            cargarUsuario = (TransferUsuario) c.ejecutaComando(null);


            if (cargarUsuario == null){
                Intent intent = new Intent().setClass(Decision.this, Registro.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent().setClass(Decision.this, MainActivity.class);
                startActivity(intent);
            }

        } catch (commandException e) {
            e.printStackTrace();
        }
        finish();
    }
}
