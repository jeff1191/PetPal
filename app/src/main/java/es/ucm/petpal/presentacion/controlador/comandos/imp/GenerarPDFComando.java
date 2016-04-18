package es.ucm.petpal.presentacion.controlador.comandos.imp;

import android.util.Log;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.negocio.post.SAPost;
import es.ucm.petpal.negocio.usuario.SAUsuario;
import es.ucm.petpal.presentacion.controlador.comandos.Command;
import es.ucm.petpal.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 21/03/2016.
 */
public class GenerarPDFComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAPost saPost = FactoriaSA.getInstancia().nuevoSAPost();
        try {
            saPost.generarPDF();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
