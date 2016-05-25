package es.ucm.petpal.presentacion.controlador.comandos.imp;

import java.util.List;

import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.negocio.post.SAPost;
import es.ucm.petpal.negocio.post.TransferPost;
import es.ucm.petpal.presentacion.controlador.comandos.Command;
import es.ucm.petpal.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by Juan Lu on 20/05/2016.
 */
public class VerPostsComando implements Command {
    @Override
    public Object ejecutaComando(Object datos) throws commandException {
        SAPost saPost= FactoriaSA.getInstancia().nuevoSAPost();
        List<TransferPost> postsList= saPost.consultarPosts();
        return postsList;

    }
}
