package es.ucm.petpal.presentacion.controlador.imp;

import android.content.Intent;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.ucm.petpal.negocio.post.TransferPost;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.controlador.Dispatcher;
import es.ucm.petpal.presentacion.controlador.ListaComandos;
import es.ucm.petpal.presentacion.vista.Ayuda;
import es.ucm.petpal.presentacion.vista.Bienvenido;
import es.ucm.petpal.presentacion.vista.Configuracion;
import es.ucm.petpal.presentacion.vista.Contexto;
import es.ucm.petpal.presentacion.vista.PetPalWebView;
import es.ucm.petpal.presentacion.vista.VerPerfil;
import es.ucm.petpal.presentacion.vista.VerPosts;


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
                intentConfiguracion.putExtra("usuarioConfig", conf);
                Contexto.getInstancia().getContext().startActivity(intentConfiguracion);
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

            case ListaComandos.VER_POSTS:
                Intent intent = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerPosts.class);
                List<TransferPost> postsModelo= (List<TransferPost>) datos;
                ArrayList<String> listaImagenes= new ArrayList<String>();
                ArrayList<Integer> listaIds = new ArrayList<Integer>();
                ArrayList<String> listaNombres = new ArrayList<String>();
                ArrayList<String> listaCiudades = new ArrayList<String>();
                ArrayList<String> listaDescripciones = new ArrayList<String>();
                ArrayList<String> listaFechas = new ArrayList<String>();
                SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
                for(int i=0; i < postsModelo.size(); i++){
                    listaImagenes.add(postsModelo.get(i).getImagen());
                    listaCiudades.add(postsModelo.get(i).getUbicacion());
                    listaIds.add(postsModelo.get(i).getId());
                    listaNombres.add(postsModelo.get(i).getTitulo());
                    listaDescripciones.add(postsModelo.get(i).getDescripcion());
                    listaFechas.add(formatFecha.format(postsModelo.get(i).getFecha()));
                }
                Bundle b = new Bundle();
                b.putIntegerArrayList("listaIds", listaIds);
                b.putStringArrayList("listaPosts", listaNombres);
                b.putStringArrayList("imagenesPosts", listaImagenes);
                b.putStringArrayList("ciudadesPosts", listaCiudades);
                b.putStringArrayList("descripcionesPosts", listaDescripciones);
                b.putStringArrayList("fechasPosts", listaFechas);
                intent.putExtras(b);
                Contexto.getInstancia().getContext().startActivity(intent);
                break;

            case ListaComandos.HAY_USUARIO:
                TransferUsuario user = (TransferUsuario) datos;
                Intent hayUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Bienvenido.class);
                if(user != null){
                    hayUsuario.putExtra("existe", true);
                    hayUsuario.putExtra("color", user.getColor());
                }
                else {
                    hayUsuario.putExtra("existe", false);
                    hayUsuario.putExtra("color", "AS_theme_azul");
                }
                hayUsuario.setFlags(hayUsuario.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
                Contexto.getInstancia().getContext().startActivity(hayUsuario);
                break;

            default:
                break;
        }
    }
}
