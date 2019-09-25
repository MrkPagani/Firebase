package com.apk.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    TextView txtArtis;
    EditText inputTrack;
    SeekBar seekRating;
    Button btnAdd;

    ListView listViewTrack;

    List<Track> tracks;
    DatabaseReference db_track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        txtArtis = findViewById(R.id.txtNamaArtis);
        inputTrack = findViewById(R.id.inputTrack);
        seekRating = findViewById(R.id.seekbarRating);
        btnAdd = findViewById(R.id.btnRating);

        listViewTrack = findViewById(R.id.listViewTrack);

        tracks = new ArrayList<>();

        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.ARTIST_ID);
        String nama = intent.getStringExtra(MainActivity.ARTIST_NAME);

        txtArtis.setText(nama);
        db_track = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrack();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        db_track.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tracks.clear();

                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren()){
                    Track track = trackSnapshot.getValue(Track.class);
                    tracks.add(track);
                }

                TrackList trackListAdapter = new TrackList(AddTrackActivity.this,tracks);
                listViewTrack.setAdapter(trackListAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveTrack(){
        String trackName = inputTrack.getText().toString().trim();
        int rating = seekRating.getProgress();

        if (!TextUtils.isEmpty(trackName)){
            String id = db_track.push().getKey();
            Track track = new Track(id,trackName,rating);
            db_track.child(id).setValue(track);

            Toast.makeText(this, "Track Saved Succesfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please insert track", Toast.LENGTH_SHORT).show();
        }
    }

}
