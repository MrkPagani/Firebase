package com.apk.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "artisname";
    public static final String ARTIST_ID = "artisid";

    EditText inputNama;
    Button btntambah;
    Spinner genre;
    ListView listViewArtis;
    DatabaseReference db_artis;
    List<Artis> artisList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNama = findViewById(R.id.inputNama);
        btntambah = findViewById(R.id.btnTambah);
        genre = findViewById(R.id.itemGenre);
        listViewArtis = findViewById(R.id.listViewArtis);
        artisList = new ArrayList<>();
        db_artis = FirebaseDatabase.getInstance().getReference("artis");

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddArtist();
            }
        });

        listViewArtis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artis artis = artisList.get(i);

                Intent intent = new Intent(getApplicationContext(),AddTrackActivity.class);
                intent.putExtra(ARTIST_ID,artis.getArtisId());
                intent.putExtra(ARTIST_NAME,artis.getArtisNama());

                startActivity(intent);
            }
        });

        listViewArtis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Artis artis = artisList.get(i);

                showUpdate(artis.getArtisId(), artis.getArtisNama());

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        db_artis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artisList.clear();

                for (DataSnapshot artisSnapshot:dataSnapshot.getChildren() ){
                    Artis artist = artisSnapshot.getValue(Artis.class);

                    artisList.add(artist);
                }
                ArtisList Adapter = new ArtisList(MainActivity.this,artisList);
                listViewArtis.setAdapter(Adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void showUpdate(final String artisId , String artisName){

        AlertDialog.Builder dialogBuild = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog,null);
        dialogBuild.setView(dialogView);

        final EditText inputEdit = dialogView.findViewById(R.id.editName);
        final Spinner spinnerEdit = dialogView.findViewById(R.id.spinEdit);
        final Button buttonEdit = dialogView.findViewById(R.id.btnUpdate);
        final Button buttonDelete = dialogView.findViewById(R.id.btnDelete);

        dialogBuild.setTitle("Update Artis " + artisName);

        final AlertDialog alertDialog = dialogBuild.create();
        alertDialog.show();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputEdit.getText().toString().trim();
                String spin = spinnerEdit.getSelectedItem().toString();

                if (TextUtils.isEmpty(name)){
                    inputEdit.setError("Nama Kosong");
                    return;
                }

                updateArtis(artisId,name,spin);

                alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteArtis(artisId);

                alertDialog.dismiss();
            }
        });

    }

    private void deleteArtis(String artisId){
        DatabaseReference db_artis = FirebaseDatabase.getInstance().getReference("artis").child(artisId);
        DatabaseReference db_track = FirebaseDatabase.getInstance().getReference("track").child(artisId);

        db_artis.removeValue();
        db_track.removeValue();

        Toast.makeText(this, "Data Deleted", Toast.LENGTH_LONG).show();
    }

    private boolean updateArtis(String id, String nama, String genre){
        DatabaseReference db_artis = FirebaseDatabase.getInstance().getReference("artis").child(id);
        Artis artis = new Artis(id,nama,genre);
        db_artis.setValue(artis);

        Toast.makeText(this, "Artis Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    public void AddArtist(){
        String nama = inputNama.getText().toString().trim();
        String Genre = genre.getSelectedItem().toString();

        if (!TextUtils.isEmpty(nama)){
            String id = db_artis.push().getKey();
            Artis artis = new Artis(id,nama,Genre);
            db_artis.child(id).setValue(artis);

            Toast.makeText(this, "Artis Ditambahkan", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Silahkan Isi Nama Terlebih Dahulu", Toast.LENGTH_SHORT).show();
        }
    }
}
