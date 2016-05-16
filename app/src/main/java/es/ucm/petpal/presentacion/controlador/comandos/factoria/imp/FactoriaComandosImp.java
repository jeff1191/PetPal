package es.ucm.petpal.presentacion.controlador.comandos.factoria.imp;

import es.ucm.petpal.presentacion.controlador.ListaComandos;
import es.ucm.petpal.presentacion.controlador.comandos.Command;
import es.ucm.petpal.presentacion.controlador.comandos.factoria.FactoriaComandos;
import es.ucm.petpal.presentacion.controlador.comandos.imp.ConfiguracionComando;
import es.ucm.petpal.presentacion.controlador.comandos.imp.ConsultarUsuarioComando;
import es.ucm.petpal.presentacion.controlador.comandos.imp.CrearPostComando;
import es.ucm.petpal.presentacion.controlador.comandos.imp.CrearUsuarioComando;
import es.ucm.petpal.presentacion.controlador.comandos.imp.EditarUsuarioComando;
import es.ucm.petpal.presentacion.controlador.comandos.imp.EnviarCorreoComando;
import es.ucm.petpal.presentacion.controlador.comandos.imp.GenerarPDFComando;
import es.ucm.petpal.presentacion.controlador.comandos.imp.VerAyudaComando;
import es.ucm.petpal.presentacion.controlador.comandos.imp.VerWebViewComando;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class FactoriaComandosImp extends FactoriaComandos {
    @Override
    public Command getCommand(String comando) {
        Command ret = null;
        switch(comando){
            case ListaComandos.CONFIGURACION:
                ret= new ConfiguracionComando();
            break;
            case ListaComandos.EDITAR_USUARIO:
                ret= new EditarUsuarioComando();
            break;
            case ListaComandos.AYUDA:
                ret = new VerAyudaComando();
                break;
            case ListaComandos.CREAR_USUARIO:
                ret = new CrearUsuarioComando();
                break;
            case ListaComandos.CONSULTAR_USUARIO:
                ret = new ConsultarUsuarioComando();
                break;
            case ListaComandos.GENERAR_PDF:
                ret = new GenerarPDFComando();
                break;
            case ListaComandos.ENVIAR_CORREO:
                ret = new EnviarCorreoComando();
                break;
            case ListaComandos.CREAR_POST:
                ret = new CrearPostComando();
                break;
            case ListaComandos.CONSULTAR_WEB_VIEW:
                ret = new VerWebViewComando();
                break;
        }

        return ret;
    }
}
