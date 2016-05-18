package es.ucm.petpal.negocio.post.imp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import es.ucm.petpal.R;
import es.ucm.petpal.integracion.DBHelper;
import es.ucm.petpal.negocio.factoria.FactoriaSA;
import es.ucm.petpal.negocio.post.SAPost;
import es.ucm.petpal.negocio.usuario.SAUsuario;
import es.ucm.petpal.negocio.usuario.TransferUsuario;
import es.ucm.petpal.presentacion.vista.Contexto;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class SAPostImp implements SAPost {

    private final static String NOMBRE_DOCUMENTO = "Informe.pdf";
    private final static String NOMBRE_DIRECTORIO = "AS";
    private DBHelper mDBHelper;

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(Contexto.getInstancia().getContext().getApplicationContext(), DBHelper.class);
        }
        return mDBHelper;
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    public static File getRuta() {
        // El fichero será almacenado en un directorio dentro del directorio Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }

    @Override
    public String generarPDF() throws IOException, DocumentException{
        SAUsuario saUsuario = FactoriaSA.getInstancia().nuevoSAUsuario();
        TransferUsuario usuario = saUsuario.consultarUsuario();
        String name = usuario.getNombre();
        Integer puntuacion = 0;
        Integer puntuacionAnterior = 0;

        Document document = new Document();
        File f = crearFichero(NOMBRE_DOCUMENTO);
        PdfWriter.getInstance(document, new FileOutputStream(f.getAbsolutePath()));
        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
        Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
        Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        document.add(new Paragraph("\n", paragraphFont));
        document.add(new Paragraph("Informe AS", titleFont));
        document.add(new Paragraph(name, paragraphFont));
        document.add(new Paragraph("\n", paragraphFont));

        // Insertamos el logo
        Bitmap bitmap = BitmapFactory.decodeResource(Contexto.getInstancia().getContext().getResources(), R.drawable.logo);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        Image imagen = Image.getInstance(stream.toByteArray());
        imagen.scaleToFit(75f, 75f);
        imagen.setAbsolutePosition(480f, 730f);
        document.add(imagen);


        // Mostramos la puntuacion anterior y la actual para comparar el progreso
        document.add(new Paragraph("Puntuacion", chapterFont));
        document.add(new Paragraph("\n", paragraphFont));
        PdfPTable table = new PdfPTable(2);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Anterior\n" + puntuacionAnterior );
        table.addCell("Actual\n" + puntuacion);

            /*///////////////////////Se me resiste el tam de la flechita///////////////////////////////
        // Flecha roja
        Bitmap bitmapRojo = BitmapFactory.decodeResource(Contexto.getInstancia().getContext().getResources(), R.drawable.flecharoja_informe);
        ByteArrayOutputStream streamRojo = new ByteArrayOutputStream();
        bitmapRojo.compress(Bitmap.CompressFormat.JPEG, 100, streamRojo);
        Image imagenRojo = Image.getInstance(streamRojo.toByteArray());
       // imagenRojo.scaleToFit(25f, 25f);
        // Flecha verde
        Bitmap bitmapVerde = BitmapFactory.decodeResource(Contexto.getInstancia().getContext().getResources(), R.drawable.flechaverde_informe);
        ByteArrayOutputStream streamVerde = new ByteArrayOutputStream();
        bitmapVerde.compress(Bitmap.CompressFormat.JPEG, 100, streamVerde);
        Image imagenVerde = Image.getInstance(streamVerde.toByteArray());
        //imagenVerde.scaleToFit(25f, 25f);
        if (puntuacion - puntuacionAnterior >= 0)
            table.addCell(Image.getInstance(imagenVerde));
        else
            table.addCell(Image.getInstance(imagenRojo));
        *///////////////////////////////////////////////////////////////////////////////////////////

    document.add(table);
    document.add(new Paragraph("\n", paragraphFont));


    // Mostramos el activity_reto
    document.add(new Paragraph("Reto", chapterFont));
    document.add(new Paragraph("\n", paragraphFont));
   /* Dao<Reto, Integer> retoDao;
    Reto reto = null;
    try {
        retoDao = getHelper().getRetoDao();
        reto = retoDao.queryForId(1);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    document.add(new Paragraph(reto.getNombre(), paragraphFont));
    document.add(new Paragraph("Contador: " + reto.getContador().toString() + "/30" , paragraphFont));
    if (reto.getSuperado())
        document.add(new Paragraph("Superado" , paragraphFont));
    document.add(new Paragraph("\n", paragraphFont));


    // Insertamos una tabla con las tareas y sus puntuaciones.
    document.add(new Paragraph("Tareas", chapterFont));
    document.add(new Paragraph("\n", paragraphFont));
    Dao<Tarea, Integer> tareaDao;
    List<Tarea> tareas = new ArrayList<Tarea>();
    try {
        tareaDao = getHelper().getTareaDao();
        tareas = tareaDao.queryForAll();
    } catch (SQLException e) {
        e.printStackTrace();
    }*/

    float[] columnWidths = {1, 5, 1, 1, 1};
    PdfPTable tabla = new PdfPTable(columnWidths);
    tabla.setWidthPercentage(100);
    tabla.getDefaultCell().setUseAscender(true);
    tabla.getDefaultCell().setUseDescender(true);

    Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
    PdfPCell cell1 = new PdfPCell(new Phrase("Nº", font));
    PdfPCell cell2 = new PdfPCell(new Phrase("Tarea", font));
    PdfPCell cell3 = new PdfPCell(new Phrase("Si", font));
    PdfPCell cell4 = new PdfPCell(new Phrase("No", font));
    PdfPCell cell5 = new PdfPCell(new Phrase("Total", font));
    cell1.setBackgroundColor(BaseColor.BLUE);
    cell2.setBackgroundColor(BaseColor.BLUE);
    cell3.setBackgroundColor(BaseColor.BLUE);
    cell4.setBackgroundColor(BaseColor.BLUE);
    cell5.setBackgroundColor(BaseColor.BLUE);
    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
    tabla.addCell(cell1);
    tabla.addCell(cell2);
    tabla.addCell(cell3);
    tabla.addCell(cell4);
    tabla.addCell(cell5);

    tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
    tabla.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
   /* for (Integer i = 1; i <= tareas.size(); i++) {
        Tarea tarea = tareas.get(i - 1);
        tabla.addCell(i.toString());
        tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla.addCell(tarea.getTextoAlarma());
        tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(tarea.getNumSi().toString());
        tabla.addCell(tarea.getNumNo().toString());
        Integer total = tarea.getNumSi() - tarea.getNumNo();
        tabla.addCell(total.toString());
    }*/
    document.add(tabla);

    document.close();
    return f.getAbsolutePath();
}

}
