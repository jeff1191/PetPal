package es.ucm.petpal.presentacion.controlador.imp;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import es.ucm.petpal.presentacion.controlador.Dispatcher;


/**
 * Created by Jeffer on 02/03/2016.
 */
public class DispatcherImp extends Dispatcher {
    @Override
    public void dispatch(String accion, Object datos) {

        switch(accion){
/*
            case ListaComandos.VER_EVENTOS:
                Intent intent = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerEventos.class);
                List<TransferEvento> eventosModelo= (List<TransferEvento>) datos;
                ArrayList<String> listaActividad= new ArrayList<String>();
                ArrayList<Integer> listaIds = new ArrayList<Integer>();
                ArrayList<Integer> listaActivos = new ArrayList<Integer>();
                for(int i=0; i < eventosModelo.size(); i++){
                    String addEvento= eventosModelo.get(i).getTextoAlarma() + " el " + eventosModelo.get(i).getTextoFecha();
                    listaActividad.add(addEvento);
                    listaIds.add(eventosModelo.get(i).getId());
                    listaActivos.add(eventosModelo.get(i).getAsistencia());
                }
                intent.putStringArrayListExtra("listaEventos", listaActividad);
                intent.putIntegerArrayListExtra("listaIds", listaIds);
                intent.putIntegerArrayListExtra("listaAsistencia", listaActivos);
                Contexto.getInstancia().getContext().startActivity(intent);
                break;

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

            case ListaComandos.VER_RETO:
                Intent intentR = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerReto.class);
                TransferReto r = (TransferReto)datos;
                if (r != null) {
                    intentR.putExtra("textReto", r.getNombre());
                    intentR.putExtra("contadorReto", r.getContador());
                    intentR.putExtra("superadoReto", r.getSuperado());
                }

                Contexto.getInstancia().getContext().startActivity(intentR);
                break;

            case ListaComandos.RESPONDER_RETO:
                break;

            case ListaComandos.VER_INFORME:
                Intent intentTareas = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerInforme.class);
                ArrayList<Object> a = (ArrayList<Object>)datos;
                TransferUsuario tu = (TransferUsuario) a.get(0);

                ArrayList<String> titulos = new ArrayList<String>();
                ArrayList<Integer> si = new ArrayList<Integer>();
                ArrayList<Integer> no = new ArrayList<Integer>();

                for(int j = 1; j < a.size(); j++){
                    TransferTarea tt = (TransferTarea)a.get(j);
                    titulos.add(tt.getTextoAlarma());
                    no.add(tt.getNumNo());
                    si.add(tt.getNumSi());
                }

                intentTareas.putExtra("puntuacion",tu.getPuntuacion() );
                intentTareas.putExtra("puntuacionAnterior", tu.getPuntuacionAnterior());
                intentTareas.putStringArrayListExtra("titulos", titulos);
                intentTareas.putIntegerArrayListExtra("no", no);
                intentTareas.putIntegerArrayListExtra("si", si);
                Contexto.getInstancia().getContext().startActivity(intentTareas);
                break;

            case ListaComandos.ACTUALIZAR_PUNTUACION:
                Intent iPuntuacion = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                iPuntuacion.putExtra("puntuacion", (Integer)datos);
                break;

            case ListaComandos.CREAR_USUARIO:
                break;

            case ListaComandos.CONSULTAR_USUARIO:
                TransferUsuario transferUsuario = (TransferUsuario)datos;
                Intent hayUsuario = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                hayUsuario.putExtra("nombre", transferUsuario.getNombre());
                hayUsuario.putExtra("correo", transferUsuario.getCorreo());
                hayUsuario.putExtra("avatar", transferUsuario.getAvatar());
                hayUsuario.putExtra("puntuacion", transferUsuario.getPuntuacion());
                hayUsuario.putExtra("puntuacionAnterior", transferUsuario.getPuntuacionAnterior());
                hayUsuario.putExtra("color", transferUsuario.getColor());
                hayUsuario.putExtra("tono", transferUsuario.getTono());
                hayUsuario.putExtra("nombre tutor", transferUsuario.getNombreTutor());
                hayUsuario.putExtra("correo tutor", transferUsuario.getCorreoTutor());
                Contexto.getInstancia().getContext().startActivity(hayUsuario);
                break;

            case ListaComandos.CARGAR_BBDD:
                break;

            case ListaComandos.GENERAR_PDF:
                break;

            case ListaComandos.ENVIAR_CORREO:
                break;
            case ListaComandos.GUARDAR_EVENTOS:
                Intent iGuardarEvento = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), MainActivity.class);
                Contexto.getInstancia().getContext().startActivity(iGuardarEvento);
                break;*/
            default:

                break;
        }
    }
}
