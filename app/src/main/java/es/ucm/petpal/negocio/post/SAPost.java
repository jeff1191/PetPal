/**
 * 
 */
package es.ucm.petpal.negocio.post;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

public interface SAPost {
    /*
    public List<TransferPost> consultarEventos();
    public TransferReto verReto();
    public Integer responderReto(Integer respuesta);
    public List<TransferPost> consultarTareas();
    public void cargarTareasBBDD();
    public void cargarRetoBBDD();
    public void cargarEventosBBDD();*/
    public String generarPDF() throws IOException, DocumentException;
   // public void guardarEventos(List<TransferPost> eventosRespuesta) ;
}