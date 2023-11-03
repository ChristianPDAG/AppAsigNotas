package com.example.eva1_asignotas;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


/*public class AsigList extends ArrayAdapter<Asignatura> {

    private Activity context;
    private List<Asignatura> asignaturaList;



    public AsigList(Activity context, List<Asignatura> asignaturaList){
        super (context, R.layout.activity_main, asignaturaList);
        this.context = context;
        this.asignaturaList = asignaturaList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_main,null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.txtAsig);

        Asignatura asignatura = asignaturaList.get(position);

        textViewName.setText(asignatura.getAsigName());

        return listViewItem;
    }
}*/
