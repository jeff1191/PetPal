/**
 * 
 */
package es.ucm.petpal.negocio.post;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface SAPost {
    public String generarPDF() throws IOException, DocumentException;

}