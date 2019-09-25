package com.apk.firebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArtisList extends ArrayAdapter<Artis> {

    private Activity context;
    private List<Artis> artisList;

    public ArtisList (Activity context,List<Artis> artisList){
        super(context,R.layout.list_layout,artisList);
        this.context = context;
        this.artisList = artisList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewNama = listViewItem.findViewById(R.id.txtNama);
        TextView textViewGenre = listViewItem.findViewById(R.id.txtGenre);

        Artis artis = artisList.get(position);

        textViewNama.setText(artis.getArtisNama());
        textViewGenre.setText(artis.getArtisGenre());

        return listViewItem;
    }

}
