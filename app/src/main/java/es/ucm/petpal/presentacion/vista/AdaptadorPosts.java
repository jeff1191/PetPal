package es.ucm.petpal.presentacion.vista;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.petpal.R;

/**
 * Created by Juan Lu on 20/05/2016.
 */
public class AdaptadorPosts extends BaseAdapter {

    private ArrayList<String> titulosPosts = new ArrayList<String>();
    private ArrayList<String> ciudadesPosts = new ArrayList<String>();
    private ArrayList<String> imagenesPosts = new ArrayList<String>();
    private ArrayList<String> fechasPosts = new ArrayList<String>();
    private LayoutInflater inflater;

    public AdaptadorPosts(Activity context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return titulosPosts.size();
    }

    @Override
    public String getItem(int position) {
        return titulosPosts.get(position);
    }

    public String getUbicacion(int position) {
        return ciudadesPosts.get(position);
    }

    public String getFoto(int position) {
        return imagenesPosts.get(position);
    }

    public String getFecha(int position) {
        return fechasPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_row_post, null);
        TextView nombrePost = (TextView) convertView.findViewById(R.id.tituloPostList);
        TextView ciudadPost = (TextView) convertView.findViewById(R.id.ciudadPostList);
        ImageView imagenPost = (ImageView) convertView.findViewById(R.id.imagePostList);
        TextView fechaPost = (TextView) convertView.findViewById(R.id.fechaPostList);

        nombrePost.setText(getItem(position));
        ciudadPost.setText(ciudadExacta(getUbicacion(position)));
        fechaPost.setText(getFecha(position));

        if(!getFoto(position).equals(""))
            imagenPost.setImageBitmap(BitmapFactory.decodeFile(getFoto(position)));
        else
            imagenPost.setImageResource(R.drawable.avatar);

        return convertView;
    }

    public void setDatos(ArrayList<String> nombresPosts) {
        titulosPosts=nombresPosts;
        notifyDataSetChanged();
    }

    public void setDatosImagenes(ArrayList<String> imagenes) {
        imagenesPosts=imagenes;
        notifyDataSetChanged();
    }

    public void setDatosCiudades(ArrayList<String> ciudades) {
        ciudadesPosts=ciudades;
        notifyDataSetChanged();
    }

    public void setDatosFechas(ArrayList<String> fechas) {
        fechasPosts=fechas;
        notifyDataSetChanged();
    }

    public String ciudadExacta(String sinEditar){
        String[] palabras = sinEditar.split(" ");
        return palabras[palabras.length-1];
    }

}
