/**
 * 
 */
package es.ucm.petpal.negocio.post;

import java.util.List;

public interface SAPost {

    void crearPost(TransferPost transferPost);

    List<TransferPost> consultarPosts();

}