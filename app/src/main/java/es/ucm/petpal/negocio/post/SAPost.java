/**
 * 
 */
package es.ucm.petpal.negocio.post;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

public interface SAPost {

    String generarPDF() throws IOException, DocumentException;

    void crearPost(TransferPost transferPost);

    List<TransferPost> consultarPosts();

}