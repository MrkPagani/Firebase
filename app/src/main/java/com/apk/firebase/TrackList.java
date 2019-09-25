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

public class TrackList extends ArrayAdapter<Track> {

    private Activity context;
    private List<Track> trackList;

    public TrackList (Activity context,List<Track> trackList){
        super(context,R.layout.list_layout_track,trackList);
        this.context = context;
        this.trackList= trackList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_track,null,true);
        TextView textViewNama = listViewItem.findViewById(R.id.txtNama);
        TextView textViewTrack = listViewItem.findViewById(R.id.txtTrack);

        Track track = trackList.get(position);

        textViewNama.setText(track.getTrackNama());
        textViewTrack.setText(track.getTrackRating()+"");

        return listViewItem;
    }

}
