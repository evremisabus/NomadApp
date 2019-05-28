package com.android.gezginapp.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.gezginapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNoteActivity extends AppCompatActivity {

    EditText etUserNote;
    Button btnAddNote;
    Button btnGotoNotesPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etUserNote = (EditText) findViewById(R.id.et_name);
        btnAddNote =(Button) findViewById(R.id.buttonAddNote);
        btnGotoNotesPage =(Button) findViewById(R.id.buttonGoToNote);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        btnGotoNotesPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addNote(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("GezdigimYerler");
        String noteId = databaseReference.push().getKey();
        String receivedUserNote = etUserNote.getText().toString();
        if(receivedUserNote.length() > 0)
        {
            databaseReference.child(noteId).child("sehiradi").setValue(receivedUserNote);
            showDialog("Başarılı", "Notunuz Kaydedildi");
        }else{
            showDialog("Başarısız","Not alanı boş bırakılamaz");
        }
        etUserNote.setText("");

    }
    private void showDialog(String title, String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddNoteActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
            builder.show();
    }
}
