package es.ucm.petpal.presentacion.controlador.imp;

import android.content.Intent;

import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.controlador.Dispatcher;
import es.ucm.petpal.presentacion.controlador.ListaComandos;
import es.ucm.petpal.presentacion.vista.Ayuda;
import es.ucm.petpal.presentacion.vista.Configuracion;
import es.ucm.petpal.presentacion.vista.Contexto;
import es.ucm.petpal.presentacion.vista.MainActivity;
import es.ucm.petpal.presentacion.vista.PetPalWebView;
import es.ucm.petpal.presentacion.vista.VerPerfil;


/**
 * Created by Jeffer on 02/03/2016.
 */
public class DispatcherImp extends Dispatcher {
    @Override
    public void dispatch(String accion, Object datos) {

        switch(accion){

            case ListaComandos.CONFIGURACION:
                Intent intentConfiguracion = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Configuracion.class);
                TransferUsuario conf = (TransferUsuario) datos;
                intentConfiguracion.putExtra("nombreConfiguracion", conf.getNombre());
                intentConfiguracion.putExtra("imagenConfiguracion", conf.getAvatar());
                intentConfiguracion.putExtra("temaConfiguracion", conf.getColor());
                Contexto.getInstancia().getContext().startActivity(intentConfiguracion);
                break;

            case ListaComandos.EDITAR_USUARIO:
                Intent intentEditarUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                TransferUsuario editarUsuario = (TransferUsuario) datos;
                intentEditarUsuario.putExtra("editarNombre", editarUsuario.getNombre());
                intentEditarUsuario.putExtra("editarAvatar", editarUsuario.getAvatar());
                Contexto.getInstancia().getContext().startActivity(intentEditarUsuario);
                break;

            case ListaComandos.AYUDA:
                Intent intentAyuda = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Ayuda.class);
                intentAyuda.putExtra("pantalla", (String)datos);
                Contexto.getInstancia().getContext().startActivity(intentAyuda);
                break;

            case ListaComandos.CONSULTAR_USUARIO:
                Intent usuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerPerfil.class);
                TransferUsuario transferUsuario = (TransferUsuario)datos;
                usuario.putExtra("usuario", transferUsuario);
                Contexto.getInstancia().getContext().startActivity(usuario);
                break;

            case ListaComandos.CONSULTAR_WEB_VIEW:
                Intent pantallaWebView = new Intent (Contexto.getInstancia().getContext().getApplicationContext(), PetPalWebView.class);
                Contexto.getInstancia().getContext().startActivity(pantallaWebView);
                break;

            default:
                break;
        }
    }
}
