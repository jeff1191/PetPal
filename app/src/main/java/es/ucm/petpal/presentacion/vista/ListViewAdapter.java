package es.ucm.petpal.presentacion.vista;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.ucm.petpal.R;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<String> titulos;
    ArrayList<Integer> si;
    ArrayList<Integer> no;
    LayoutInflater inflater;
    Context context;

    public ListViewAdapter(ArrayList<String> titulos, ArrayList<Integer> si,
                           ArrayList<Integer> no, Context context) {
        this.titulos = titulos;
        this.si = si;
        this.no = no;
        this.context = context;
    }

    @Override
    public int getCount() {
        return titulos.size();
    }

    @Override
    public Object getItem(int position) {
        return titulos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        TextView txtTitle;  // texto alarma
        TextView sit;       // numero de si
        TextView not;       // numero de no
        TextView total;     // balance del progreso (si - no = total)

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_row_tarea, parent, false);

        // Locacalizar en el listview_item.xml
        txtTitle = (TextView) itemView.findViewById(R.id.tarea);
        sit = (TextView) itemView.findViewById(R.id.si);
        not = (TextView) itemView.findViewById(R.id.no);
        total = (TextView) itemView.findViewById(R.id.total);

        // Dar valores a los TextView
        txtTitle.setText(titulos.get(position));
        String ss = si.get(position).toString();
        sit.setText(ss);
        String sn = no.get(position).toString();
        not.setText(sn);
        Integer t = si.get(position)-no.get(position);
        if(t >= 0)
            total.setTextColor(Color.parseColor("#2fb518"));
        else
            total.setTextColor(Color.parseColor("#2fb518"));
        total.setText(t.toString());

        return itemView;
    }
}